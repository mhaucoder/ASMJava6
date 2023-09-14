package edu.poly.controller.VNPayController;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.poly.dao.BookticketsDAO;
import edu.poly.dao.BookticketsdetailDAO;
import edu.poly.dao.MovieDAO;
import edu.poly.dao.MovieplayDAO;
import edu.poly.dao.UserDAO;
import edu.poly.entity.Account;
import edu.poly.entity.Booktickets;
import edu.poly.entity.Bookticketsdetail;
import edu.poly.entity.Mail;
import edu.poly.services.MailerHelper;
import edu.poly.services.MailerService;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/vnpay_jsp")
public class VNpayController {
    @Autowired
    HttpSession session;

    @Autowired
    BookticketsDAO btDAO;

    @Autowired
    BookticketsdetailDAO btdDAO;

    @Autowired
    MovieDAO mDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    MovieplayDAO mpDAO;

    @Autowired
    MailerService mailerService;

    @RequestMapping("/index")
    public String index() {
        return "vnpay_jsp/index";
    }

    @RequestMapping("/vnpay_pay")
    public String ajaxServletRenderView() {
        return "vnpay_jsp/vnpay_pay";
    }

    @RequestMapping("/vnpay_return")
    public String returnRenderView(HttpServletRequest request, HttpServletResponse resp, Model model)
            throws ServletException, IOException {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName),
                    StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);

        model.addAttribute("vnp_TxnRef", request.getParameter("vnp_TxnRef"));
        model.addAttribute("vnp_Amount", request.getParameter("vnp_Amount"));
        model.addAttribute("vnp_OrderInfo", request.getParameter("vnp_OrderInfo"));
        model.addAttribute("vnp_ResponseCode", request.getParameter("vnp_ResponseCode"));
        model.addAttribute("vnp_TransactionNo", request.getParameter("vnp_TransactionNo"));
        model.addAttribute("vnp_BankCode", request.getParameter("vnp_BankCode"));
        model.addAttribute("vnp_PayDate", request.getParameter("vnp_PayDate"));
        String status = "";
        if (signValue.equals(vnp_SecureHash)) {
            if (Config.codeReturn.equals(request.getParameter("vnp_TransactionStatus"))) {
                // Ghi nhận đơn hàng thành công lên database
                status = "Thành công";

                List<Integer> seatsArray = (List<Integer>) session.getAttribute("seatsArray");
                String theaterId = (String) session.getAttribute("theaterId");
                Integer moviePlayId = (Integer) session.getAttribute("moviePlayId");

                // Thực hiện các thao tác hoàn tất thanh toán và lưu vào cơ sở dữ liệu
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String id = authentication.getName();
                Account account = userDAO.findById(id).get();

                Booktickets booktickets = new Booktickets();
                booktickets.setDayBook(new Date());
                booktickets.setQuantity(seatsArray.size());
                booktickets.setTotal(
                        mpDAO.findById(moviePlayId).get().getMovie().getPrice().intValue() * seatsArray.size());
                booktickets.setUserBo(account);
                booktickets.setMovieplayBook(mpDAO.findById(moviePlayId).get());
                btDAO.save(booktickets);
                List<Bookticketsdetail> bookticketsdetails = new ArrayList<Bookticketsdetail>();
                for (Integer soGhe : seatsArray) {
                    Bookticketsdetail bookticketsdetail = new Bookticketsdetail();
                    bookticketsdetail.setBookticket(booktickets);
                    bookticketsdetail.setSoGhe(soGhe);
                    bookticketsdetails.add(bookticketsdetail);
                }
                btdDAO.saveAll(bookticketsdetails);
                // Gửi thông tin về email
                MailerHelper helper = new MailerHelper();
                List<File> files = new ArrayList<>();
                String[] emailCC = helper.parseStringEmailToArray("");
                String[] emailBCC = helper.parseStringEmailToArray("");
                Mail mail = new Mail();
                mail.setTo(account.getEmail());
                mail.setCc(emailCC);
                mail.setBcc(emailBCC);
                mail.setSubject("Thông tin vé xem phim");
                String tenPhong = mpDAO.findById(moviePlayId).get().getRoomMp().getId();
                String tenRap = mpDAO.findById(moviePlayId).get().getRoomMp().getMovietheaterRoom().getName();
                String tenPhim = mpDAO.findById(moviePlayId).get().getMovie().getName();
                String gioChieu = mpDAO.findById(moviePlayId).get().getStartTime();
                String maVe = "123456789211221412309";
                String content = "Kính chào quý khách,"
                        + "<div>" + "Quý khác đã đặt vé xem phim thành công, xin vui lòng kiểm tra thông tin vé phim : "
                        + tenPhim + "</div>"
                        + "<Phòng>" + "Tại Rạp : " + tenRap + ", phòng : " + tenPhong + ", giờ chiếu : " + gioChieu
                        + "</div>"
                        + "<div>" + "Mã vé phim : " + maVe + "</div>"
                        + "<div>" + "Quý khách hãy đi đúng giờ để không bỏ lỡ suất phim" + "</div>";
                mail.setContent(content);
                try {
                    mailerService.send(mail);
                    model.addAttribute("email", account.getEmail());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                // Xóa thông tin đã lưu trong session sau khi hoàn tất thanh toán
                session.removeAttribute("seatsArray");
                session.removeAttribute("theaterId");
                session.removeAttribute("movieId");

            } else {
                // Ghi nhận đơn hàng không thành công lên database
                status = "Không thành công";
            }
        } else {
            // Ghi nhận đơn hàng không thành công lên database
            status = "invalid signature";
        }
        model.addAttribute("vnp_status", status);
        return "vnpay_jsp/vnpay_return";
    }

    @RequestMapping("/vnpay_payment")
    public String payment(HttpServletRequest req, HttpServletResponse resp, Model model,
            @RequestParam List<Integer> seats,
            @RequestParam String theaterId,
            @RequestParam Integer moviePlayId) {

        // Tạo hoặc lấy session hiện tại
        HttpSession session = req.getSession();
        // Lưu thông tin vào session
        session.setAttribute("seatsArray", seats);
        session.setAttribute("theaterId", theaterId);
        session.setAttribute("moviePlayId", moviePlayId);

        int sessionTimeoutSeconds = 20 * 60;
        session.setMaxInactiveInterval(sessionTimeoutSeconds);
        model.addAttribute("cacghe", seats);
        model.addAttribute("soghe", seats.size());
        model.addAttribute("priceBook", mpDAO.findById(moviePlayId).get().getMovie().getPrice().intValue());
        model.addAttribute("priceBooks",
                mpDAO.findById(moviePlayId).get().getMovie().getPrice().intValue() * seats.size());
        return "forward:/vnpay_jsp/vnpay_pay";
    }

    @PostMapping("/vnpayajax")
    public void doPostajaxServlet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = Integer.parseInt(req.getParameter("amount")) * 100;
        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", Config.codeReturn);
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(job));

    }

}
