package gawonjoo0.clothinkbeta;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by USER on 2015-11-18.
 */
public class Laundry extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cloth,container,false);

//        Button loginbtn=(Button) view.findViewById(R.id.loginBtn);
//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity().getBaseContext(),"로그인되었습니다.",Toast.LENGTH_LONG);
//                Intent intent=new Intent(getActivity().getBaseContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }

}
