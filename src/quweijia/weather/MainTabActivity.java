package quweijia.weather;

import java.util.Map;

import quweijia.weather.core.Weather;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainTabActivity extends TabActivity implements OnTabChangeListener {

	private TabHost myTabhost;
	protected int myMenuSettingTag = 0;
	protected Menu myMenu;
	private Button btn_chengshi;
	private EditText edit_chengshi;
	private TextView text_chengshi;
	private Weather wc;
	private TextView text_wendu;
	private TextView text_tianqi_status;
	private TextView text_fengxiang;
	private TextView text_fengsu;
	private LinearLayout edit_btn_area;
	private TextView text_kongqi;
	private TextView text_jianyi;

	private static final int myMenuResources[] = { R.menu.menu_tianqi,
			R.menu.menu_kongqi, R.menu.menu_jianyi, R.menu.menu_qushi };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		wc = new Weather();

	}

	private void initView() {
		// TODO Auto-generated method stub
		myTabhost = this.getTabHost();
		// get Tabhost
		LayoutInflater.from(this).inflate(R.layout.main,
				myTabhost.getTabContentView(), true);
		// myTabhost.setBackgroundColor(Color.argb(150, 22, 70, 150));

		myTabhost.addTab(myTabhost.newTabSpec("tianqi")// make a new Tab
				.setIndicator(getString(R.string.tianqi))
				// set the Title and Icon
				.setContent(R.id.tab_tianqi));
		// set the layout

		myTabhost.addTab(myTabhost.newTabSpec("kongqi")// make a new Tab
				.setIndicator(getString(R.string.kongqi))
				// set the Title and Icon
				.setContent(R.id.tab_kongqi));
		// set the layout

		myTabhost.addTab(myTabhost.newTabSpec("jianyi")// make a new Tab
				.setIndicator(getString(R.string.jianyi))
				// set the Title and Icon
				.setContent(R.id.tab_jianyi));
		myTabhost.addTab(myTabhost.newTabSpec("qushi")// make a new Tab
				.setIndicator(getString(R.string.qushi))
				// set the Title and Icon
				.setContent(R.id.tab_qushi));
		// set the layout

		myTabhost.setOnTabChangedListener(this);

		// 获取各view的句柄
		text_chengshi = (TextView) findViewById(R.id.text_chengshi);
		edit_chengshi = (EditText) findViewById(R.id.edit_chengshi);
		btn_chengshi = (Button) findViewById(R.id.btn_chengshi);
		text_wendu = (TextView) findViewById(R.id.text_wendu);
		text_tianqi_status = (TextView) findViewById(R.id.text_tianqi_status);
		text_fengsu = (TextView) findViewById(R.id.text_fengsu);
		text_fengxiang = (TextView) findViewById(R.id.text_fengxiang);
		edit_btn_area = (LinearLayout) findViewById(R.id.edit_btn_area);
		text_kongqi = (TextView)findViewById(R.id.text_kongqi);
		text_jianyi = (TextView) findViewById(R.id.text_jianyi);

		text_chengshi.setClickable(true);
		text_chengshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 隐藏城市view
				text_chengshi.setVisibility(View.INVISIBLE);
				// 显示城市输入框和确定按钮
				edit_btn_area.setVisibility(View.VISIBLE);
				// edit_chengshi.setVisibility(View.VISIBLE);
				// btn_chengshi.setVisibility(View.VISIBLE);

			}

		});
		btn_chengshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 输入城市后获取数据并更新ui
				updateData();
				// 隐藏城市输入框和确定按钮
				edit_btn_area.setVisibility(View.INVISIBLE);
				// edit_chengshi.setVisibility(View.INVISIBLE);
				// btn_chengshi.setVisibility(View.INVISIBLE);
				// 显示城市显示view
				text_chengshi.setVisibility(View.VISIBLE);
				// showMsg("run here" + text_chengshi.getVisibility());
				// showMsg(wc.testNet(),10000);

			}

		});
	}

	protected void updateData() {
		// TODO 更新数据并显示
//		获取输入框的输入城市名称
		String city = edit_chengshi.getText().toString();
		if (city == null || "".equalsIgnoreCase(city)) {
			showMsg("城市名称不能为空");
		} else {
			// 给Weather对象设置城市
			wc.setCity(city);

			// 通过api获取各天气数据
			if (wc.checkout()) {
				// 把数据分别更新到ui上面
				tianqiDisplay(wc.getWeatherMap());
				kongqiDisplay(wc.getKongqiMap());
			} else {
				showMsg("获取失败,请检查网络或接口是否可用");
			}
		}

	}

	private void kongqiDisplay(Map<String, String> m) {
		// TODO 更新空气质量相关界面
		if(m.containsKey("kongqi")){
			text_kongqi.setText(m.get("kongqi"));
		}
		
	}

	private void tianqiDisplay(Map<String, String> weatherMap) {
		// TODO 更新天气质量相关界面
		Map<String, String> m = weatherMap;
		if (m.containsKey("city")) {
			text_chengshi.setText(m.get("city"));
		}
		if (m.containsKey("status1")) {
			text_tianqi_status.setText(m.get("status1"));
		}
		if (m.containsKey("direction1")) {
			text_fengxiang.setText(m.get("direction1"));
		}
		if (m.containsKey("power1")) {
			text_fengsu.setText(m.get("power1"));
		}
		if(m.containsKey("temperature1")){
			text_wendu.setText(m.get("temperature1"));
		}
		
		if(m.containsKey("chy_shuoming")){
			text_jianyi.setText("穿衣建议："+m.get("chy_shuoming"));
		}
		if(m.containsKey("yd_s")){
			text_jianyi.append("\n\n运动说明："+m.get("yd_s"));
		}
		if(m.containsKey("gm_l")){
			text_jianyi.append("\n\n感冒指数:"+m.get("gm_l"));
		}
		if(m.containsKey("gm_s")){
			text_jianyi.append("\n\n感冒指数说明:"+m.get("gm_s"));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Hold on to this
		myMenu = menu;
		myMenu.clear();// 清空MENU菜单
		// Inflate the currently selected menu XML resource.
		MenuInflater inflater = getMenuInflater();
		// 从TabActivity这里获取一个MENU过滤器
		switch (myMenuSettingTag) {
		case 1:
			inflater.inflate(myMenuResources[0], menu);
			// 动态加入数组中对应的XML MENU菜单
			break;
		case 2:
			inflater.inflate(myMenuResources[1], menu);
			break;
		case 3:
			inflater.inflate(myMenuResources[2], menu);
			break;
		case 4:
			inflater.inflate(myMenuResources[3], menu);
			break;

		default:
			inflater.inflate(myMenuResources[0], menu);
			break;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onTabChanged(String tagString) {
		// TODO Auto-generated method stub
		if (tagString.equals("tianqi")) {
			myMenuSettingTag = 1;
		}
		if (tagString.equals("kongqi")) {
			myMenuSettingTag = 2;
		}
		if (tagString.equals("jianyi")) {
			myMenuSettingTag = 3;
		}
		if (tagString.equals("qushi")) {
			myMenuSettingTag = 4;
		}
		if (myMenu != null) {
			onCreateOptionsMenu(myMenu);
		}
	}

	void showMsg(String txt, int time) {
		Toast.makeText(this, txt, time).show();
	}

	void showMsg(String txt) {
		Toast.makeText(this, txt, 1000).show();
	}

}
