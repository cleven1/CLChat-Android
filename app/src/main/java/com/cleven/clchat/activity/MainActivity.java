package com.cleven.clchat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cleven.clchat.R;
import com.cleven.clchat.base.CLBaseActivity;
import com.cleven.clchat.base.CLBaseFragment;
import com.cleven.clchat.fragment.CLContactFragment;
import com.cleven.clchat.fragment.CLDiscoverFragment;
import com.cleven.clchat.fragment.CLHomeFragment;
import com.cleven.clchat.fragment.CLProfileFragment;
import com.cleven.clchat.manager.CLMQTTManager;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CLBaseActivity {

    private FrameLayout frameLayout;
    private RadioGroup radioGroup;

    private int currentSelectIndex = 0;
    /// 记录当前显示的fragment
    private CLBaseFragment preFragment;
    private List<CLBaseFragment> fragments;
    private TextView centerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /// 取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMqtt();

        setupTitleBar();

        initFragments();

        findViews();

    }

    /// 连接MQTT
    private void initMqtt() {
        CLMQTTManager.getInstance().connectMQTT(this);
    }
    
    private void findViews() {

        frameLayout = (FrameLayout)findViewById( R.id.frameLayout );
        radioGroup = (RadioGroup)findViewById( R.id.radioGroup );
        /// 设置监听事件
        radioGroup.setOnCheckedChangeListener(new MyOnClickListener());
        /// 默认选中第一个
        radioGroup.check(R.id.rb_main_home);
    }

    private void setupTitleBar(){
        CommonTitleBar titleBar = (CommonTitleBar) findViewById(R.id.titlebar);
        ImageButton leftImageButton = titleBar.getLeftImageButton();
        leftImageButton.setVisibility(View.GONE);
        TextView rightTextView = titleBar.getRightTextView();
        rightTextView.setVisibility(View.GONE);
        centerTextView = titleBar.getCenterTextView();
        centerTextView.setText("首页");
    }

    private class MyOnClickListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_main_home:
                    currentSelectIndex = 0;
                    centerTextView.setText("首页");
                    break;
                case R.id.rb_main_contact:
                    currentSelectIndex = 1;
                    centerTextView.setText("联系人");
                    break;
                case R.id.rb_main_discover:
                    currentSelectIndex = 2;
                    centerTextView.setText("发现");
                    break;
                case R.id.rb_main_profile:
                    currentSelectIndex = 3;
                    centerTextView.setText("我");
                    break;
            }
            /// 获取Fragment
            CLBaseFragment fragment = getFragment(currentSelectIndex);
            /// 切换
            switchFragment(preFragment,fragment);
        }

    }

    /// 根据下标获取fragment
    private CLBaseFragment getFragment(int index){
        if (fragments != null && fragments.size() > 0){
            return fragments.get(index);
        }
        return null;
    }

    /// 切换Fragment
    private void switchFragment(Fragment fromFragment, CLBaseFragment nextFragment){
        if (preFragment != nextFragment){
            preFragment = nextFragment;
            if (nextFragment != null){
                /// 获取Fragment管理器
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // 判断nextFragment是否添加
                if (!nextFragment.isAdded()){
                    //隐藏当前Fragment
                    if (fromFragment != null){
                        transaction.hide(fromFragment);
                    }
                    // 添加下一个Fragment
                    transaction.add(R.id.frameLayout,nextFragment).commit();
                }else {
                    //隐藏当前Fragment
                    if (fromFragment != null){
                        transaction.hide(fromFragment);
                    }
                    /// 如果已经添加过,直接显示
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new CLHomeFragment());
        fragments.add(new CLContactFragment());
        fragments.add(new CLDiscoverFragment());
        fragments.add(new CLProfileFragment());
    }


}
