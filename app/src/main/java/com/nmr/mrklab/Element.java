package com.nmr.mrklab;

public class Element implements Comparable<Element> {
    private final String name;
    private final String desc;
    private final String amt;

    public Element(String name, String desc, String amt) {
        this.name = name;
        this.desc = desc;
        this.amt = amt;
    }

    // Override compareTo() for sorting by amount
    @Override
    public int compareTo(Element e) {
        return Integer.compare(Integer.parseInt(this.amt), Integer.parseInt(e.amt));
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getAmt() { return amt + "%"; }

    public Integer getIntAmt() { return Integer.parseInt(amt); }
}

