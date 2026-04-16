package org.acme.application.dto;

public class CreateTaskListDto {
    private String title;
    private String color;
    private String icon;

    public CreateTaskListDto() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
