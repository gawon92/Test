package gawonjoo0.clothinkbeta;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by USER on 2015-11-18.
 */

public class Cloth extends Fragment{

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.laundry,container,false);


//        Button joinbtn=(Button) view.findViewById(R.id.joinOk);
//        joinbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View v){
//                Toast.makeText(getActivity().getApplicationContext(),"가입을 축하드립니다!",Toast.LENGTH_LONG).show();
//                //가입하면 첫화면으로 돌아감
//                Intent intent=new Intent(getActivity().getBaseContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });

        Button button1=(Button) view.findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.findViewById(R.id.layout1).setVisibility(View.GONE);
                v.findViewById(R.id.layout2).setVisibility(View.VISIBLE);

            }
        });

        return view;
    }
}
