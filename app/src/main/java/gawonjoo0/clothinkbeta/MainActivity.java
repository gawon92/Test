package gawonjoo0.clothinkbeta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends Activity {
    TextView dynamicTv;
    TextView timeTv,tempTv;
    LinearLayout scrollLinear;
    LinearLayout dynamicLinear;

    Document doc=null;

    public static NodeList nodeList;
//    public static float temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.laundrypage)!=null){
            if(savedInstanceState!=null){
                return ;
            }
            Laundry laundrypage=new Laundry();
            laundrypage.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction().add(R.id.main_container, laundrypage).commit();
        }

        Weather weather=new Weather();
//        weather.setDaemon(true);
        weather.start();
//        GetXMLTask task=new GetXMLTask(MainActivity.this);
//        task.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4111755000");

    }

    class Weather extends Thread{
        public void run(){
            GetXMLTask task=new GetXMLTask(MainActivity.this);
            task.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4111755000");
        }
    }

    public void selectFragment(View view){
        Fragment fr=null;
        switch (view.getId()){
            case R.id.laundrypage:
                fr=new Laundry();
                break;
            case R.id.clothpage:
                fr=new Cloth();
                break;
            case R.id.managepage:
                fr=new Manage();
                break;
        }

        FragmentManager fm=getFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_container,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @SuppressLint("NewApi")
    private class GetXMLTask extends AsyncTask<String, Void, Document>{
        private Activity context;

        public GetXMLTask(Activity context){
            this.context=context;
        }

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try{
                url=new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance().newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Log.i("날씨파싱", "에러");
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            nodeList=doc.getElementsByTagName("data");

//            for(int i=0; i<nodeList.getLength(); i++){
//                Node node = nodeList.item(0);
//                Element fstElemnt = (Element) node;
//                NodeList nameList = fstElemnt.getElementsByTagName("temp");
//                Element nameElement = (Element) nameList.item(0);
//                nameList = nameElement.getChildNodes();
//
//                float temperature = Float.parseFloat(((Node) nameList.item(0)).getNodeValue().toString());
//            }
//            Log.i("nodeList 길이",nodeList.getLength()+"");

            String s="";
            scrollLinear=(LinearLayout)findViewById(R.id.scrollLinear);

            for(int i=0;i<nodeList.getLength();i++){
                s += "" + i + ": 날씨정보: ";
                Node node = MainActivity.nodeList.item(i);
                Element fstElemnt = (Element) node;
                NodeList nameList = fstElemnt.getElementsByTagName("temp");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();

                s += "온도=" + ((Node) nameList.item(0)).getNodeValue() + " ,";

                NodeList websiteList = fstElemnt.getElementsByTagName("wfKor");
                Element websiteElement = (Element) websiteList.item(0);
                websiteList = websiteElement.getChildNodes();
                s += "날씨=" + ((Node) websiteList.item(0)).getNodeValue() + "\n";

                NodeList timeList = fstElemnt.getElementsByTagName("hour");
                Element timeElement = (Element) timeList.item(0);
                timeList = timeElement.getChildNodes();
                ((Node) timeList.item(0)).getNodeValue();

                dynamicLinear=new LinearLayout(MainActivity.this);
                dynamicLinear.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
                dynamicLinear.setOrientation(LinearLayout.VERTICAL);
                dynamicTv = new TextView(MainActivity.this);
                dynamicTv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
                dynamicTv.setPadding(20, 10, 10, 10);

                if (((Node) websiteList.item(0)).getNodeValue().equals("구름 조금")) {
                    dynamicTv.setBackgroundResource(R.drawable.smallcloud);
                } else if (((Node) websiteList.item(0)).getNodeValue().equals("구름 많음")) {
                    dynamicTv.setBackgroundResource(R.drawable.bigcloud);
                } else if (((Node) websiteList.item(0)).getNodeValue().equals("맑음")) {
                    dynamicTv.setBackgroundResource(R.drawable.sunny);
                } else if (((Node) websiteList.item(0)).getNodeValue().equals("흐림")) {
                    dynamicTv.setBackgroundResource(R.drawable.clouding);
                } else if (((Node) websiteList.item(0)).getNodeValue().equals("비")||((Node) websiteList.item(0)).getNodeValue().equals("눈/비")) {
                    dynamicTv.setBackgroundResource(R.drawable.sunnyrain);
                } else if (((Node) websiteList.item(0)).getNodeValue().equals("눈")) {
                    dynamicTv.setBackgroundResource(R.drawable.snow);
                }

                timeTv = new TextView(MainActivity.this);
                timeTv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                timeTv.setTextSize(12);
                timeTv.setTextColor(Color.parseColor("#000000"));
                timeTv.setGravity(Gravity.CENTER_HORIZONTAL);
                timeTv.setText(((Node) timeList.item(0)).getNodeValue() + "시");

                tempTv = new TextView(MainActivity.this);
                tempTv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tempTv.setTextSize(12);
                tempTv.setPadding(0, 0, 0, 22);
                tempTv.setTextColor(Color.parseColor("#000000"));
                tempTv.setGravity(Gravity.CENTER_HORIZONTAL);
                tempTv.setText(((Node) nameList.item(0)).getNodeValue() + "℃");

                dynamicLinear.addView(timeTv);
                dynamicLinear.addView(dynamicTv);
                dynamicLinear.addView(tempTv);
                scrollLinear.addView(dynamicLinear);

            }

            super.onPostExecute(doc);
        }
    }

}
