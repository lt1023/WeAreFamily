package com.wearefamily.view;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.wearefamily.R;
import com.wearefamily.application.BombApplication;
import com.wearefamily.base.BaseFragment;
import com.wearefamily.ui.chat.NotesActivity;
import com.wearefamily.ui.family.MyFamilyActivity;
import com.wearefamily.ui.robot.RobotActivity;
import com.wearefamily.ui.weather.WeatherActivity;

/**
 * Created by Administrator on 2017/10/26.
 */
public class HomeFragment extends BaseFragment {
    private TextView mHomeFamily;
    private TextView mHomeNotes;
    private TextView mHomeRobot;
    private TextView mHomeWeather;

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        setOnClick(mHomeFamily);
        setOnClick(mHomeNotes);
        setOnClick(mHomeRobot);
        setOnClick(mHomeWeather);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews() {
        mHomeFamily = findView(R.id.home_family);
        mHomeNotes = findView(R.id.home_notes);
        mHomeRobot = findView(R.id.home_robot);
        mHomeWeather = findView(R.id.home_weather);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.home_family:
                startActivity(new Intent(getContext(), MyFamilyActivity.class));
                break;
            case R.id.home_notes:
                startActivity(new Intent(getContext() , NotesActivity.class));
                break;
            case R.id.home_robot:
                startActivity(new Intent(getContext() , RobotActivity.class));
                break;
            case R.id.home_weather:
                startActivity(new Intent(getContext() , WeatherActivity.class));
                break;
        }
    }
}
