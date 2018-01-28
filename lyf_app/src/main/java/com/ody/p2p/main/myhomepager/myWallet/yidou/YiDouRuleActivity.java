package com.ody.p2p.main.myhomepager.myWallet.yidou;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.main.R;

/**
 * Created by meijunqiang on 2017/3/30.
 * 描述：伊豆规则
 */

public class YiDouRuleActivity extends BaseActivity {

    private TextView mYidou_rule_tv;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_yidou_rule;
    }

    @Override
    public void initView(View view) {
        mYidou_rule_tv = (TextView) findViewById(R.id.yidou_rule_text);
    }

    @Override
    public void doBusiness(Context mContext) {
        mYidou_rule_tv.setText(Html.fromHtml("<section class=\"ui-container\" v-cloak>\n" +
                "    <div class=\"f12 pdL10 pdR10 c3 pdT15 text-justify\">\n" +
                "        <p class=\"f14 c6 lineH180 mgT5\">一、什么是伊豆？</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">1.伊豆是仅限在来伊份APP使用，可用于APP内活动参与、社区互动、兑换等。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">2.伊豆可在“我的”中查看伊豆数量及伊豆明细。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">3.用户未使用的伊豆，来伊份将在后年的1月1日清零。例如2018年1月1日清零2016年1月1日至2016年12月31日的所有伊豆。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">4.来伊份积分可以按一定比例兑换成伊豆。</p>\n" +
                "\n" +
                "        <p class=\"f14 c6 lineH180 mgT20\">二、伊豆如何获得？</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">1.用户成功注册来伊份APP均可获得伊豆。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">2.用户登录来伊份APP签到可以获得伊豆。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">3.用户分享来伊份APP商品、活动等行为可以获得伊豆。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">4.用户完成订单好评并晒图可以获得伊豆。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">5.用户在来伊份App社区内进行互动可以获得伊豆，比如评论、点赞、分享、关注、发布等。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">6.每天伊豆发放数量有限，送完为止。</p>\n" +
                "\n" +
                "        <p class=\"f14 c6 lineH180 mgT20\">三、伊豆如何使用？</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">1.伊豆可以在APP内参与抽奖等活动时使用。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">2.伊豆可以用于兑换优惠券，按券额进行相应消耗。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">3.来伊份APP社区内用户之间的打赏可以使用伊豆。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">4.来伊份APP其他线上活动时可以使用伊豆。</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">5.伊豆的使用数量视不同活动或互动行为情况而定。</p>\n" +
                "\n" +
                "        <p class=\"f14 c6 lineH180 mgT20\">四、其他说明</p>\n" +
                "        <p class=\"f12 c6 lineH180 mgT5\">伊豆规则最终解释权归来伊份所有。</p>\n" +
                "    </div>\n" +
                "</section>\n"));
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
