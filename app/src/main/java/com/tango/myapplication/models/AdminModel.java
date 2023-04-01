package com.tango.myapplication.models;

public class AdminModel
{
    private String name;
    private String password;

    public AdminModel(){};
    public AdminModel(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
