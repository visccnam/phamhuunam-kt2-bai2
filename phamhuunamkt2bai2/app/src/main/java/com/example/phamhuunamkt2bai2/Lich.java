package com.example.phamhuunamkt2bai2;

public class Lich
{
    public String id,name,dates,times,ischecked;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDates() {
        return dates;
    }

    public String getTimes() {
        return times;
    }

    public String getIschecked() {
        return ischecked;
    }

    public Lich(String id, String name, String dates, String times, String ischecked) {
        this.id = id;
        this.name = name;
        this.dates = dates;
        this.times = times;
        this.ischecked = ischecked;
    }

    public Lich() {
    }
}

