package com.systemvx.androiddevtest.ui.orderManagement;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.systemvx.androiddevtest.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;


import com.systemvx.androiddevtest.ui.orderManagement.adapter.FragmentAdapter;
import com.systemvx.androiddevtest.ui.orderManagement.fragment.OrderGetFragment;
import com.systemvx.androiddevtest.ui.orderManagement.fragment.OrderListFragment;
import com.systemvx.androiddevtest.ui.orderManagement.fragment.OrderPayFragment;
import com.systemvx.androiddevtest.ui.orderManagement.fragment.OrderSendFragment;
import com.systemvx.androiddevtest.ui.orderManagement.fragment.OrderServiceFragment;
import com.systemvx.androiddevtest.ui.orderManagement.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;



public class OrderActivity extends FragmentActivity {

    private List<Fragment> mFragmentList;//五个界面的集合
    private FragmentAdapter mFragmentAdapter;//界面适配器
    private CustomViewPager vp_order;//自定义viewpager
    private int page;//得到选择的页码
    private RadioGroup rg_order;//顶部页码
    /**
     * Tab显示内容TextView
     */
    private RadioButton tv_order_all, tv_order_pay, tv_order_send, tv_order_get,tv_order_service;
    //顶部导航栏的集合
    private ArrayList<Integer> tabList;
    /**
     * Tab的那个引导线
     */
    private ImageView mTabLineIv;
    /**
     * Fragment
     */
    private OrderListFragment mOrderListFragment;//全部订单
    private OrderPayFragment mOrderPayFragment;//待支付
    private OrderSendFragment mOderSendFragment;//待发货
    private OrderGetFragment mOderGetFragment;//待收货
    private OrderServiceFragment mOderServiceFragment;//售后服务
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        initView();
        initData();
        initTabLineWidth();
    }

    /**
     * 设置滑动条的宽度为屏幕的1/5(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 5;
        mTabLineIv.setLayoutParams(lp);
    }

    private void initData() {
        /**
         * 进入时候根据page值设置导航栏选中哪个
         */
        initDataForTopPage();
        /**
         * 加载引导页的数据
         */
        mFragmentList = new ArrayList<Fragment>();
        mOrderListFragment = new OrderListFragment();
        mOrderPayFragment = new OrderPayFragment();
        mOderSendFragment = new OrderSendFragment();
        mOderGetFragment = new OrderGetFragment();
        mOderServiceFragment = new OrderServiceFragment();

        mFragmentList.add(mOrderListFragment);
        mFragmentList.add(mOrderPayFragment);
        mFragmentList.add(mOderSendFragment);
        mFragmentList.add(mOderGetFragment);
        mFragmentList.add(mOderServiceFragment);
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp_order.setAdapter(mFragmentAdapter);
        vp_order.setCurrentItem(page);
        vp_order.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                        .getLayoutParams();
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记5个页面,
                 * 从左到右分别为0,1,2,3,4
                 * 0->1; 1->2; 2->3; 3->4; 4->3; 3->2;2->1; 1->0
                 */
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - positionOffset)
                            * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - positionOffset)
                            * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));
                } else if (currentIndex == 2 && position == 2) // 2->3
                {
                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));
                } else if (currentIndex == 3 && position == 3) // 3->4
                {
                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));
                } else if (currentIndex == 3 && position == 2) // 3->2
                {
                    lp.leftMargin = (int) (-(1 - positionOffset)
                            * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));

                } else if (currentIndex == 4 && position == 3) // 4->3
                {
                    lp.leftMargin = (int) (-(1 - positionOffset)
                            * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));
                } else if (currentIndex == 4 && position == 4) // 4->3
                {
                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 5) + currentIndex
                            * (screenWidth / 5));
                }

                mTabLineIv.setLayoutParams(lp);
            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        tv_order_all.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        tv_order_pay.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        tv_order_send.setTextColor(Color.BLUE);
                        break;
                    case 3:
                        tv_order_get.setTextColor(Color.BLUE);
                        break;
                    case 4:
                        tv_order_service.setTextColor(Color.BLUE);
                        break;
                }
                currentIndex = position;
            }

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /**
         * 顶部导航界面切换的监听
         */
        rg_order.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.tv_order_all:
                        vp_order.setCurrentItem(0);
                        break;
                    case R.id.tv_order_pay:
                        vp_order.setCurrentItem(1);
                        break;
                    case R.id.tv_order_send:
                        vp_order.setCurrentItem(2);
                        break;
                    case R.id.tv_order_get:
                        vp_order.setCurrentItem(3);
                        break;
                    case R.id.tv_order_service:
                        vp_order.setCurrentItem(4);
                        break;
                }
            }
        });
    }

    private void initDataForTopPage() {
        vp_order.setCurrentItem(0);
        tabList = new ArrayList<>();
        tabList.add(R.id.tv_order_all);
        tabList.add(R.id.tv_order_pay);
        tabList.add(R.id.tv_order_send);
        tabList.add(R.id.tv_order_get);
        tabList.add(R.id.tv_order_service);
        rg_order.check(tabList.get(page));
        resetTextView();
        switch (page){
            case 0:
                tv_order_all.setTextColor(Color.BLUE);
                break;
            case 1:
                tv_order_pay.setTextColor(Color.BLUE);
                break;
            case 2:
                tv_order_send.setTextColor(Color.BLUE);
                break;
            case 3:
                tv_order_get.setTextColor(Color.BLUE);
                break;
            case 4:
                tv_order_service.setTextColor(Color.BLUE);
                break;
        }
        currentIndex = page;
    }

    private void initView() {
        vp_order = (CustomViewPager)findViewById(R.id.vp_order);
        tv_order_all = (RadioButton)findViewById(R.id.tv_order_all);
        tv_order_pay = (RadioButton)findViewById(R.id.tv_order_pay);
        tv_order_send = (RadioButton)findViewById(R.id.tv_order_send);
        tv_order_get = (RadioButton)findViewById(R.id.tv_order_get);
        tv_order_service = (RadioButton)findViewById(R.id.tv_order_service);
        mTabLineIv = (ImageView)findViewById(R.id.mTabLineIv);
        rg_order = (RadioGroup)findViewById(R.id.rg_order);
        page = getIntent().getIntExtra("page", -1);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        tv_order_all.setTextColor(Color.parseColor("#777777"));
        tv_order_pay.setTextColor(Color.parseColor("#777777"));
        tv_order_send.setTextColor(Color.parseColor("#777777"));
        tv_order_get.setTextColor(Color.parseColor("#777777"));
        tv_order_service.setTextColor(Color.parseColor("#777777"));
    }
}
