use master
go

drop database if exists J6ASM
go

create database J6ASM
go

use J6ASM
go

create table [Role] (
                        id varchar(50) primary key,
                        [role] varchar(10),
                        active bit default 1
)
go

create table [User] (
                        id varchar(50) primary key,
                        [password] varchar(50),
                        [name] nvarchar(50),
                        phone varchar(11),
                        age int,
                        email varchar(50),
                        active bit default 1
)

create table Authority (
                           id int identity(1,1) primary key,
                           [user_id] varchar(50),
                           role_id varchar(50),
                           foreign key ([user_id]) references [User](id),
                           foreign key (role_id) references [Role](id)
)
go

create table Movie (
                       id nvarchar(50) primary key,
                       [name] nvarchar(50),
                       [description] nvarchar(255),
                       poster varchar(255),
                       categorys nvarchar(255),
                       actors nvarchar(255),
                       release_day date,
                       price float,
                       duration float,
                       active bit default 1
)
go

create table Movietheater (
                              id nvarchar(50) primary key,
                              [name] nvarchar(50),
                              phone varchar(11),
                              email varchar(50),
                              [address] nvarchar(50),
                              [description] nvarchar(255),
                              active bit default 1
)
go

create table Room (
                      id varchar(10) primary key,
                      mt_id nvarchar(50),
                      [name] nvarchar(50),
                      so_ghe int default 30,
                      active bit default 1,
                      foreign key (mt_id) references Movietheater(id)
)
go

create table Movieplay (
                           id int identity(1,1) primary key,
                           room_id varchar(10),
                           m_id nvarchar(50),
                           start_day date,
                           start_time varchar(50),
                           foreign key (room_id) references Room(id),
                           foreign key (m_id) references Movie(id)
)
go

create table Booktickets (
                             id int identity(1,1) primary key,
                             [user_id] varchar(50),
                             total float,
                             day_book date,
                             quantity int,
                             mp_id int,
                             foreign key ([user_id]) references [User](id),
                             foreign key (mp_id) references Movieplay(id)
)
go

create table Bookticketsdetail (
                                   id int identity(1,1) primary key,
                                   bt_id int,
                                   so_ghe int,
                                   foreign key (bt_id) references Booktickets(id),
)
go

--////////////////////////////////////////////////////////////

-- Insert data into [Role] table
INSERT INTO [Role] (id, [role])
VALUES ('admin', 'Admin'),
       ('user', 'User');
go

-- Insert data into [User] table
INSERT INTO [User] (id, [password], [name], phone, age)
VALUES ('an123',  'an123',     N'Nguyễn Văn An', '1234567890', 30),
       ('binh123',  'binh123',   N'Trần Thị Bình', '9876543210', 25),
       ('chau123',  'chau123',   N'Lê Minh Châu', '1112223333', 35),
       ('user4',  'duong123',  N'Phạm Thanh Dương', '4445556666', 28),
       ('user5',  'diem123',   N'Hoàng Thị Diễm', '7778889999', 40),
       ('user6',  'dung123',   N'Vũ Xuân Dũng', '1110009999', 32),
       ('user7',  'ha123',     N'Ngô Thị Hà', '4443332222', 27),
       ('user8',  'hai123',    N'Đặng Văn Hải', '9998887777', 29),
       ('user9',  'hoa123',    N'Bùi Thị Hoa', '3332221111', 33),
       ('user10', 'hieu123',   N'Đỗ Minh Hiếu', '6665554444', 31),
       ('user11', 'hong123',   N'Trương Thị Hồng', '2221110000', 36),
       ('user12', 'hung123',   N'Lý Văn Hùng', '5554443333', 26),
       ('user13', 'kim123',    N'Trần Thị Kim', '8887776666', 34),
       ('user14', 'linh123',   N'Nguyễn Văn Linh', '2223334444', 30),
       ('user15', 'lan123',    N'Phạm Thị Lan', '5556667777', 32),
       ('user16', 'long123',   N'Hoàng Văn Long', '8889990000', 27),
       ('user17', 'mai123',    N'Vũ Thị Mai', '3334445555', 35),
       ('user18', 'nguyen123', N'Ngô Minh Nguyên', '6667778888', 29),
       ('user19', 'nga123',    N'Đinh Thị Nga', '2223334444', 33),
       ('user20', 'phong123',  N'Lê Văn Phong', '5556667777', 31);
go

-- Insert data into Authority table
INSERT INTO Authority ([user_id], role_id)
VALUES ('an123', 'admin'),
       ('binh123', 'user');
go

-- Insert data into Movie table
INSERT INTO Movie (id, [name], [description], poster, categorys, actors, release_day, price, duration)
VALUES ('movie1', N'Thám Tử Lừng Danh Conan-Tàu Ngầm Sắt Đen', N'đang đứng đầu doanh thu phòng vé tại Nhật Bản, phá vỡ hàng loạt kỉ lục của những người anh em trước. Phim đạt doanh thu hơn 3 tỷ yên chỉ trong cuối tuần mở màn đầu tiên',                                                                                       'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002703?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Hoạt hình', 'Yamazaki Wakana, Hayashibara Megumi, Takayama Minami', '2023-08-01', 45000.0, 1.5),
       ('movie2', N'ELEMENTAL-Xứ sở các nguyên tố',            N'Ember, một cô nàng cá tính, thông minh, mạnh mẽ và đầy sức hút. Tuy nhiên mối quan hệ của cô với Wade- một anh chàng hài hước, luôn thuận thế đẩy dòng - khiến Ember cảm thấy ngờ vực với thế giới này..',                                                       'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002677?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Phiêu Lưu, Hài, Hoạt Hình', 'Mamoudou Athie, Leah Lewis, Wendi McLendon-Covey', '2023-07-01', 55000, 1.5),
       ('movie3', N'RUBY GILLMAN : TEENAGE KRAKEN',            N'Ruby Gillman, một cô bé 16 tuổi đáng yêu, vụng về khao khát được hòa nhập tại trường trung học Oceanside, nhưng tại đây cô hầu như chỉ cảm thấy mình vô hình. Ngược lại một sinh viên mới chuyển đến là Chelsea với gương mặt xinh đẹp đã nhận được sự yêu mến', 'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002711?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Hoạt hình', 'Toni Collette, Jane Fonda', '2023-08-01', 65000, 1.5),
       ('movie4', N'SPIDER-MAN: ACROSS THE SPIDER-VERSE',      N'Miles Morales đến Đa vũ trụ, nơi anh chạm trán với một nhóm Người Nhện chịu trách nhiệm bảo vệ sự tồn tại của họ. Khi các anh hùng xung đột về cách đối phó với mối đe dọa mới, Miles phải xác định lại ý nghĩa của việc trở thành anh hùng...',                 'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002632?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Hoạt hình', 'Shameik Moore, Hailee Steinfeld', '2023-07-01', 55000, 1.5),
       ('movie5', N'TROLLS BAND TOGETHER',                     N'Sự xuất hiện của John Dory,anh trai thất lạc đã lâu của Branch đã mở quá khứ đen tối của Branch,đó là về một ban nhạc có tên BroZone từng rất nổi tiếng nhưng đã tan rã.Hành trình đi tìm lại các thành viên khi xưa trở thành chuyến phiêu lưu đầy cảm xúc',    'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002751?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Hoạt hình', 'Anna Kendrick, Justin Timberlake', '2023-08-01', 40000, 1.5),
       ('movie6', N'LOVE MY SCENT',                            N'ChangSoo, một chàng trai bình thường bỗng thay đổi khi anh xịt một loại nước hoa bí ẩn do người lạ mang đến và thu hút được mọi cô gái và Ara đã yêu anh mà không rõ lý do. Mọi thứ hoàn hảo cho đến khi James tiết lộ với Ara mọi bí mật của ChangSoo',         'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002705?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Tình Cảm, Lãng Mạn', 'Yoon Shi-Yoon, Seol In-ah', '2023-07-01', 70000, 1.5),
       ('movie7', N'HOME FOR RENT',                            N'Ning,Kwin cùng cô con gái Ing chuyển đến căn hộ chung cư giá rẻ và rồi Ning nhận thấy chồng bắt đầu có những hành vi bất thường cùng hình xăm kì quái ở ngực cùng sự hiểm nguy rình rập cô con gái nhỏ. Bí mật nào che giấu dưới căn nhà thuê của họ ?',         'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002720?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Kinh Dị', 'Nittha Jirayungyurn, Sukollawat Kanaros', '2023-08-01', 85000, 1.5),
       ('movie8', N'INSIDIOUS : THE RED DOOR',                 N'10 năm sau các sự kiện xảy ra ở Quỷ Quyệt 2, khi Dalton, cậu con trai của Josh đã trưởng thành, chuẩn bị bước vào ngưỡng cửa đại học và phần còn lại của gia đình Lambert phải đối mặt với hậu quả từ quyết định của họ trong quá khứ cách đây gần một thập kỉ', 'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002696?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Kinh Dị', 'Ty Simpkins', '2023-08-01', 45000, 1.5),
       ('movie9', N'RALLY ROAD RACERS',                        N'Zhi,một chú cu li chậm chạp có niềm say mê với tốc độ. Ngôi làng nơi Zhi và bà ngoại sống đang đứng trước nguy cơ bị phá hủy.Khi đó,Zhi đã đặt cược với Vainglorious-nhà đương kim vô địch,Zhi có bốn ngày để thi đấu và chiến thắng để cứu lấy ngôi làng',      'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002723?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Hoạt hình', 'Chloe Bennet, J.K Simmons', '2023-08-01', 55000, 1.5),
       ('movie10', N'STREAMER',                                N'Năm người streamer nổi tiếng cùng nhau đến căn nhà hoang để khám phá sự thật về một đoạn phim kinh dị được đăng tải bởi một streamer nổi tiếng , họ đều phát sóng trực tiếp sự việc trong cùng một thời điểm và những sự việc quỷ dị bắt đầu xuất hiện.',        'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002731?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Kinh Dị', 'Jun-Hyung Kim, Mo-Beom Kim', '2023-08-01', 64000, 1.5),
       ('movie11', N'THE EQUALIZER 3',                         N'Robert McCall chuyển đến sinh sống tại miền Nam nước Ý nhưng phát hiện ra bạn bè của mình bị một tổ chức mafia ở địa phương kiểm soát. Khi tính mạng của họ bị đe dọa, ông buộc phải quay trở lại con đường sát thủ để bảo vệ bạn bè',                           'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002698?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Hành Động', 'Denzel Washington, Dakota Fanning', '2023-07-01', 70000, 1.5),
       ('movie12', N'THE MARVELS',                             N'Carol Danvers bị vướng vào sức mạnh của Kamala Khan và Monica Rambeau, buộc họ phải hợp tác với nhau để cứu vũ trụ',                                                                                                                                             'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002701?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Phiêu Lưu', 'Brie Larson, Samuel L. Jackson', '2023-08-01', 46000, 1.5),
       ('movie13', N'OPPENHEIMER',                             N'Mang đến một trải nghiệm kịch tính hồi hộp khi một người đàn ông bí ẩn phải mạo hiểm hủy diệt thế giới chỉ để cứu chính nó. Câu chuyện kể về nhà khoa học người Mỹ J. Robert Oppenheimer và vai trò của ông trong việc phát triển bom nguyên tử',                'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002704?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Doanh Nhân', 'Matt Damon, Robert Downey Jr', '2023-08-01', 35000, 1.5),
       ('movie14', N'THE HAUNTED MANSION',                     N'Người mẹ đơn thân tên Gabbie đã thuê một hướng dẫn viên du lịch, một nhà ngoại cảm, một linh mục và một nhà sử học để giúp trừ tà cho căn biệt thự mới mua của cô sau khi phát hiện ra nó có ma',                                                                'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002735?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Hài', 'Jamie Lee Curtis, Owen Wilson', '2023-08-01', 50000, 1.5),
       ('movie15', N'MONSTER',								  N'Khi thấy con trai bắt đầu có những hành vi lạ, mẹ của Minato tìm hiểu và phát hiện nguyên nhân bắt nguồn từ một giáo viên của trường và rồi sự thật dần được tiết lộ: vấn nạn học đường, kì thị đồng tính... tạo nên những con quái vật trong mỗi con người',    'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002725?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Kịch', 'Eita Nagayama, Sakura Ando', '2023-08-01', 45000, 1.5),
       ('movie16', N'ĐẤT RỪNG PHƯƠNG NAM',                     N'Sau bao ngày chờ đợi, dự án điện ảnh gợi ký ức tuổi thơ của nhiều thế hệ người Việt chính thức tung hình ảnh đầu tiên đầy cảm xúc. First look poster khắc họa hình ảnh đối lập: bé An đang ôm chặt mẹ giữa một khung cảnh chạy giặc loạn lạc...',                'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002699?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Tài Liệu', N'Hồng Ánh', '2023-08-01', 45000, 1.5),
       ('movie17', N'CHIẾM ĐOẠT',							  N'Người vợ của một gia đình thượng lưu thuê cô bảo mẫu “trong mơ” để chăm sóc con trai mình. Nhưng cô không ngờ rằng, phía sau sự trong sáng, tinh khiết kia, cô bảo mẫu luôn che giấu âm mưu nhằm phá hoại hạnh phúc gia đình và khiến cuộc sống của cô thay đổi','http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002733?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', 'Drama', N'Phương Anh Đào, Miu Lê', '2023-08-01', 45000, 1.5),
       ('movie18', N'DEVIL',									  N'Thanh tra Jae-Hwan sau khi mất đi anh rể và cộng sự anh đã điên cuồng truy vết kẻ giết người nguy hiểm đó và cuối cùng anh cũng trực tiếp đối mặt với tên giết người hàng loạt Jin-Hyuk và nghiệt ngã thay họ đã bị hoán đổi thân phận'					,'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002737?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Tội Phạm', 'Oh Dae-hwan, Jang Dong-yoon', '2023-07-01', 45000, 1.5),
       ('movie19', N'FANTI',                                   N'Thế giới của cô diễn viên trẻ đảo lộn khi một kẻ ẩn danh đội lốt fan bước ra từ chiếc điện thoại và bắt đầu theo dõi, quấy phá, thậm chí đe doạ cuộc sống của cô',                                                                                               'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002719?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Drama', N'Hồ Thu Anh, Nguyễn Lâm Thảo Tâm', '2023-08-01', 45000, 1.5),
       ('movie20', N'GRAN TURISMO',                            N'Jann Mardenborough, một tay đua cừ khôi tới từ Anh.Vào năm 2011, anh đã đánh bại 90.000 đối thủ để trở thành nhà vô địch, đồng thời là nhà vô địch trẻ tuổi nhất trong giải đấu GT Academy, anh trở thành đại diện cho Nissan tại giải Dubai 24-Hour',			 'http://booking.bhdstar.vn/CDN/media/entity/get/FilmPosterGraphic/HO00002729?referenceScheme=HeadOffice&allowPlaceHolder=true&height=500', N'Hành Động', 'David Harbour, Darren Barnet', '2023-08-01', 45000, 1.5);
go

INSERT INTO Movietheater (id, [name], phone, email, [address], [description])
VALUES ('theater1', N'BHD Star Quang Trung',   '0938977157',      'bhd1@gmail.com',        N'190 Quang Trung, Gò Vấp, Thành phố Hồ Chí Minh',   N'Vincom Plaza Quang Trung'),
       ('theater2', N'BHD Star Cineplex',		'0987654320',      'bhd2@gmail.com',        N'3 Đ. 3 Tháng 2, Quận 10, Thành phố Hồ Chí Minh',   N'Vincom Plaza 3 tháng 2'),
       ('theater3', N'BHD STAR THAO DIEN',     '02837446969',     'bhd3@gmail.com',        N'159 XL Hà Nội,  Quận 2, Thành phố Hồ Chí Minh',    N'Tầng 5, TTTM Vincom Mega Mall'),
       ('theater4', N'Bhd Star Lê Văn Việt',   '4445556666',      'bhd4@gmail.com',        N'Lê Văn Việt, Quận 9, Thành phố Hồ Chí Minh',       N' Vincom Plaza'),
       ('theater5', N'BHD Creative Space',     '0933133192',   'bhd5@gmail.com',        N'KĐT Sala, Thủ Đức, Thành phố Hồ Chí Minh',         N' 15-17 Đ. Số 10'),
       ('theater6', N'BHDX THÀNH PHỐ LONG KHÁNH', '1110009999',   'bhd6@gmail.com',        N'QL1A, Xuân Hoà, Long Khánh, Đồng Nai',             N' 462 QL1A, Xuân Hoà');
go

INSERT INTO Room (id, mt_id, [name], so_ghe)
VALUES ('A1', 'theater1', 'Room 1', '30'),
       ('A2', 'theater1', 'Room 2', '30'),
       ('B1', 'theater2', 'Room 1', '30'),
       ('B2', 'theater2', 'Room 2', '30'),
       ('C1', 'theater3', 'Room 1', '30'),
       ('C2', 'theater3', 'Room 2', '30'),
       ('D1', 'theater4', 'Room 1', '30'),
       ('D2', 'theater4', 'Room 2', '30'),
       ('E1', 'theater5', 'Room 1', '30'),
       ('E2', 'theater5', 'Room 2', '30');
go

-- Insert data into Movie_Play table
INSERT INTO Movieplay (room_id, m_id, start_day, start_time)
VALUES
    ('A1', 'movie1', '2023-08-01','06:00:00'),
    ('A1', 'movie1', '2023-08-01','07:45:00'),
    ('A1', 'movie1', '2023-08-01','09:30:00'),
    ('A2', 'movie2', '2023-08-01','06:00:00'),
    ('A2', 'movie2', '2023-08-01','07:45:00'),
    ('B2', 'movie1', '2023-08-01','06:00:00'),
    ('B2', 'movie5', '2023-08-01','06:45:00')
go

-- Insert data into Book_Tickets table
INSERT INTO Booktickets ([user_id],  total, mp_id, day_book, quantity)
VALUES ('an123', 80000, 1, '2023-07-15', 2),
       ('binh123', 45000, 1, '2023-07-16',  1),
       ('an123', 45000, 2, '2023-07-17', 1),
       ('binh123', 80000, 2, '2023-07-18', 2),
       ('an123', 165000, 4, '2023-07-19', 3),
       ('binh123', 110000, 4, '2023-07-20', 2),
       ('an123', 40000, 7, '2023-07-21', 1),
       ('binh123', 135000, 6, '2023-07-22', 3)
go

-- Insert data into Book_Tickets_Details table
INSERT INTO Bookticketsdetail (bt_id, so_ghe)
VALUES (1, 1),
       (1, 2),
       (2, 5),
       (3, 9),
       (4, 3),
       (4, 2),
       (5, 3),
       (5, 2),
       (5, 1),
       (6, 7),
       (6, 8),
       (7, 2),
       (8, 23),
       (8, 24),
       (8, 25)
go
