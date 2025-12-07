package com.example.tenderservice.constants;

public class TenderConstants {

    private TenderConstants() {}

    public static final String STATUS_200 = "200";
    public static final String STATUS_201 = "201";
    public static final String STATUS_404 = "404";
    public static final String STATUS_417 = "417";
    public static final String STATUS_500 = "500";

    public static final String MESSAGE_201 = "Tender created successfully";
    public static final String MESSAGE_200 = "Tender processed successfully";
    public static final String MESSAGE_404 = "Tender not found";
    public static final String MESSAGE_417_UPDATE = "Update failed. Please retry or contact support.";
    public static final String MESSAGE_417_DELETE = "Delete failed. Please retry or contact support.";
    public static final String MESSAGE_500 = "Internal server error. Please try again later";
}