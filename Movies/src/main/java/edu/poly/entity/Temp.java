package edu.poly.entity;

import java.util.List;

import lombok.Data;

@Data
public class Temp {
	private List<Room> rooms;
    private String theaterId;
	private String moviePlayId;
}
