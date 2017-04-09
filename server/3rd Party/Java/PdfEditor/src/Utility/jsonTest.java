package Utility;


import Utility.JSON.simple.JSONArray;
import Utility.JSON.simple.JSONObject;
import Utility.JSON.simple.parser.JSONParser;
import Utility.JSON.simple.parser.ParseException;

import java.util.Objects;

/**
 * Created by jcvar on 4/9/2017.
 */
public class jsonTest {
    public static void main(String[] args) throws ParseException {

        JSONParser parser = new JSONParser();

        String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";


        JSONArray array = (JSONArray) parser.parse(s);

        System.out.println(array.get(0));

        System.out.println(array.get(1));

        JSONObject obj = (JSONObject)array.get(1);

        System.out.println(obj.get("1"));


        JSONObject obj2 = (JSONObject) obj.get("1");
        System.out.println(obj2.get("2"));

        String n = "{\"jorge\":5,\"ale\":3}";
        JSONObject obj3 =  (JSONObject) parser.parse(n);

        System.out.println(obj3.get("jorge33"));
    }
}
