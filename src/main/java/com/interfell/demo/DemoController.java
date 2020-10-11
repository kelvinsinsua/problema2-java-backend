package com.interfell.demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
class DemoController {


  @PostMapping("/")
  public HashMap<String, HashMap> main(@RequestBody Map<String, String> payload) throws ParseException {
    HashMap<String, HashMap> map = new HashMap<>();
    HashMap<String, String> map2 = new HashMap<>();
    try{
    String time = payload.get("time");
    String timezone = payload.get("timezone");
    
    
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = dateFormat.parse(time);
    
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    char first = timezone.charAt(0);
    
    if(first == '+'){
        cal.add(Calendar.HOUR_OF_DAY, - Integer.parseInt(timezone.substring(1)));
    }else if(first == '-'){
        cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(timezone.substring(1)));
    }else{
        map2.put("error", "Bad Request");
        map.put("response", map2);
        return map;
    }
    
    Date finaldate = cal.getTime(); 
    map2.put("time", dateFormat.format(finaldate));
    map2.put("timezone", "utc");
    map.put("response",map2);
    return map;
    }catch(NumberFormatException | ParseException e){
        map2.put("error", "Bad Request");
        map.put("response", map2);
        return map;
    }
  }

}
