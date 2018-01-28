package com.ody.p2p.base;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ody.p2p.BuildConfig;
import com.ody.p2p.R;
import com.ody.p2p.okhttputils.UbtEvent;
import com.ody.p2p.okhttputils.UbtGsonutils;
import com.ody.p2p.okhttputils.UbtPageData;
import com.ody.p2p.okhttputils.UbtUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.views.ProgressDialog.ProgressDialog;
import com.ody.p2p.views.odyscorllviews.OdyScrollGridView;
import com.ody.p2p.views.odyscorllviews.OdyScrollListView;
import com.ody.p2p.views.odyscorllviews.OdyScrollView;
import com.ody.p2p.views.odyscorllviews.OdyScrollWebView;
import com.ody.p2p.views.odyscorllviews.OdySnapPageLayout;

import java.util.Random;

/**
 * Fragment基类(V4包)
 *
 * @author lxs
 * @version 1.0
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {

    /**
     * 当前Fragment渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 共通操作
     **/
    public Operation mBaseOperation = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    protected ProgressDialog progressDialog;

    public boolean bActive = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "BaseFragment-->onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseFragment-->onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "BaseFragment-->onCreateView()");

        //渲染视图View(防止切换时重绘View)
        if (null != mContextView) {
            ViewGroup parent = (ViewGroup) mContextView.getParent();
            if (null != parent) {
                parent.removeView(mContextView);
            }
        } else {
            // 控件初始化
            try {
                mContextView = inflater.inflate(bindLayout(), null);
                initView(mContextView);
            } catch (Exception e) {
                getActivity().finish();
                return mContextView;
            }
            //实例化共通操作
            mBaseOperation = new Operation(getActivity());
        }
        initFailed();
        //初始化presenter
        initPresenter();
        //业务处理
        doBusiness(getActivity());
        return mContextView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "BaseFragment-->onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "BaseFragment-->onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "BaseFragment-->onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "BaseFragment-->onResume()");
        super.onResume();
    }


    private void initUbdData() {

        UbtEvent ubtEvent = new UbtEvent();
        ubtEvent.v = BuildConfig.VERSION_NAME;
        ubtEvent.ut = OdyApplication.getLoginUt();
        ubtEvent.did = OdyApplication.getDeviceId();
        ubtEvent.cha = "ANDROID";
        ubtEvent.of = "";
        ubtEvent.inf = "home";
        ubtEvent.pid = "home";
        ubtEvent.ct = System.currentTimeMillis() + "";
        ubtEvent.bt = "pv";
        ubtEvent.ev = "17";
        ubtEvent.tv = "1.0";

        Random rnd = new Random();
        int tempInt = rnd.nextInt(25);
        UbtPageData ubtPageData = new UbtPageData();
        ubtPageData.pn = tempInt + "";
        ubtPageData.pt = TAG;
        ubtPageData.pcd = "";
        ubtPageData.sk = "";
        ubtPageData.cid = "";
        ubtPageData.cms = "";
        ubtPageData.oc = "";
        ubtPageData.sn = "";
        ubtPageData.ocha = "";
        ubtPageData.ru = "";
        ubtPageData.rf = "";
        ubtPageData.rp = "";
        ubtPageData.ppid = tempInt + "";
        ubtPageData.cpu = TAG;
        ubtPageData.cost = "";
        ubtPageData.st = System.currentTimeMillis() + "";
        ubtPageData.en = "enterPage";

        ubtEvent.data = UbtGsonutils.toJson(ubtPageData);

        UbtUtils.init(ubtEvent);


    }


    @Override
    public void onPause() {
        Log.d(TAG, "BaseFragment-->onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "BaseFragment-->onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "BaseFragment-->onDestroy()");
        hideLoading();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        bActive = false;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "BaseFragment-->onDetach()");
        super.onDetach();
    }

    /**
     * 获取当前Fragment依附在的Activity
     *
     * @return
     */
    public Activity getContext() {
        return getActivity();
    }

    /**
     * 获取共通操作机能
     */
    public Operation getOperation() {
        return this.mBaseOperation;
    }

    /**
     * 显示载入框
     *
     * @param msg
     */
    public void showLoading(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext(), msg);
            progressDialog.show();
        } else {
            progressDialog.show();
        }
    }

    /**
     * 取消载入框
     */
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            initUbdData();
            if (iv_top != null) {
                iv_top.setVisibility(View.GONE);
            }
            showFailed(false, 1);
        } else {
            if (iv_top != null) {
                if (showFlag) {
                    iv_top.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext(), getString(R.string.loading_waiting));
            progressDialog.show();
        } else {
            progressDialog.show();
        }
    }


    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    public View viewFailed;
    protected TextView tv_faild;
    public LinearLayout ll_notdataH5;

    protected void showFailed(boolean showFlag, int type) {
        if (viewFailed != null) {
            viewFailed.setVisibility(showFlag ? View.VISIBLE : View.GONE);
            if (type == 0) {
                tv_faild.setText(R.string.a_o_load_faile);
            } else if (type == 1) {
                tv_faild.setText(R.string.Network_convulsions_please_checking);
            }
        }
    }

    protected void showFailed(boolean showFlag, int type, int marginBottom) {
        if (viewFailed != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) viewFailed.getLayoutParams();
            lp.setMargins(0, PxUtils.dipTopx(43), 0, PxUtils.dipTopx(marginBottom));
            viewFailed.setVisibility(showFlag ? View.VISIBLE : View.GONE);
            if (type == 0) {
                tv_faild.setText(R.string.a_o_load_faile);
            } else if (type == 1) {
                tv_faild.setText(R.string.Network_convulsions_please_checking);
            }
        }
    }

    public void initFailed() {
        View view = (ViewGroup) getContext().findViewById(android.R.id.content);
        if (view instanceof FrameLayout) {
            viewFailed = LayoutInflater.from(mContextView.getContext()).inflate(R.layout.view_failed_load, null);
            if (viewFailed == null) {
                return;
            }
            viewFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAgain();
                }
            });
            tv_faild = (TextView) viewFailed.findViewById(R.id.tv_faild);
            ll_notdataH5 = (LinearLayout) viewFailed.findViewById(R.id.ll_notdataH5);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.TOP | Gravity.RIGHT;
            lp.setMargins(0, PxUtils.dipTopx(43), 0, 0);
            viewFailed.setLayoutParams(lp);
            ((FrameLayout) view).addView(viewFailed);
            viewFailed.setVisibility(View.GONE);
        }
    }

    public void setFailedMargins(int left, int top, int right, int bottom) {
        if (viewFailed == null) {
            return;
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.TOP | Gravity.RIGHT;
        lp.setMargins(PxUtils.dipTopx(left), PxUtils.dipTopx(top), PxUtils.dipTopx(right), PxUtils.dipTopx(bottom));
        viewFailed.setLayoutParams(lp);
    }

    protected ImageView iv_top;
    protected boolean showFlag;

    public void showTop(final View viewScrolled) {
        View view = (ViewGroup) getContext().findViewById(android.R.id.content);
        if (view instanceof FrameLayout) {
            iv_top = new ImageView(getContext());
            iv_top.setImageResource(R.drawable.bg_bt_top);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            lp.setMargins(0, 0, PxUtils.dipTopx(15), PxUtils.dipTopx(105));
            iv_top.setLayoutParams(lp);
            iv_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewScrolled instanceof RecyclerView) {
                        ((RecyclerView) viewScrolled).smoothScrollToPosition(0);
                    } else if (viewScrolled instanceof ScrollView) {
                        ((ScrollView) viewScrolled).smoothScrollTo(0, 0);
                    } else if (viewScrolled instanceof WebView) {
                        viewScrolled.scrollTo(0, 0);
                    } else if (viewScrolled instanceof ListView) {
                        ((ListView) viewScrolled).smoothScrollToPosition(0);
                    } else if (viewScrolled instanceof GridView) {
                        ((GridView) viewScrolled).smoothScrollToPosition(0);
                    }
                }
            });
            ((FrameLayout) view).addView(iv_top);
            iv_top.setVisibility(View.GONE);
            if (viewScrolled instanceof RecyclerView) {
                ((RecyclerView) viewScrolled).addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (recyclerView.getChildCount() > 0) {
//                            Log.d("tag", recyclerView.getChildAt(0).getY() + "****");
                            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                                int po = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                                if (po > 0 || po == -1) {
                                    showBtnTop();
                                } else {
                                    hideBtnTop();
                                }
                            }
                            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                                int po = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                                if (po > 0 || po == -1) {
                                    showBtnTop();
                                } else {
                                    hideBtnTop();
                                }
                            }
                        }
                    }
                });
            } else if (viewScrolled instanceof OdyScrollView) {
                ((OdyScrollView) viewScrolled).setScrollListener(new OdyScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdyScrollView scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            } else if (viewScrolled instanceof OdyScrollListView) {
                ((OdyScrollListView) viewScrolled).setScrollListener(new OdyScrollListView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdyScrollListView scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            } else if (viewScrolled instanceof OdyScrollGridView) {
                ((OdyScrollGridView) viewScrolled).setScrollListener(new OdyScrollGridView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdyScrollGridView scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            } else if (viewScrolled instanceof OdyScrollWebView) {
                ((OdyScrollWebView) viewScrolled).setScrollListener(new OdyScrollWebView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdyScrollWebView scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            } else if (viewScrolled instanceof OdySnapPageLayout) {
                ((OdySnapPageLayout) viewScrolled).setScrollListener(new OdySnapPageLayout.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdySnapPageLayout scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            }
        }
    }


    public void listener(int x, int y) {
        if (y > 0) {
            showBtnTop();
        } else {
            hideBtnTop();
        }
    }

    /**
     * 显示置顶按钮
     */
    private void showBtnTop() {
        // TODO Auto-generated method stub
        showFlag = true;
        if (iv_top.getVisibility() == View.GONE) {
            iv_top.setVisibility(View.VISIBLE);
//            iv_top.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_enter));
            Animator animator = AnimatorInflater.loadAnimator(getContext(), R.anim.btn_enter);
            animator.setTarget(iv_top);
            animator.start();
        }
    }

    /**
     * 隐藏置顶按钮
     */
    private void hideBtnTop() {
        // TODO Auto-generated method stub
        showFlag = false;
        if (iv_top.getVisibility() == View.VISIBLE) {
            iv_top.setVisibility(View.GONE);
            iv_top.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_exit));
//            Animator animator = AnimatorInflater.loadAnimator(getContext(), R.anim.btn_exit);
//            animator.setTarget(iv_top);
//            animator.start();
        }
    }

    public String getNetTAG() {
        return getActivity().getLocalClassName();
    }

    protected void loadAgain() {

    }

}
