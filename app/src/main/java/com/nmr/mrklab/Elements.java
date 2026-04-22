package com.nmr.mrklab;

import java.util.Comparator;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Elements {
    ArrayList<Element> elementList = new ArrayList<>();

    public String rooter;

    public void setRooter(String rooter) {

        this.rooter = rooter;
    }

    public ArrayList<Element> getElementList() {
        build();
        elementList.sort(Comparator.comparingInt(Element::getIntAmt).reversed());
        return elementList;
    }

    public void build() {
        String filthy;
        String clean;
        JSONArray jsonArray;
        final Fetcher fetcher = new Fetcher();
        fetcher.setIp(rooter);
        filthy = fetcher.fetch();
        clean = filthy.replaceAll("[\\n\\t\\r]", "");
        try {
            jsonArray = new JSONArray(clean);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj;
            try {
                obj = jsonArray.getJSONObject(i);
                elementList.add(new Element(obj.getString("name"), obj.getString("desc"), obj.getString("amt")));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
