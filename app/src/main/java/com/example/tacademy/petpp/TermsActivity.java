package com.example.tacademy.petpp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tacademy.petpp.base.BaseActivity;

public class TermsActivity extends BaseActivity {

    TextView title_text, termsText;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // actList에 B를 추가해줍니다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        // 상단바
        title_text = (TextView)findViewById(R.id.title_text);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setVisibility(View.INVISIBLE);

        termsText = (TextView)findViewById(R.id.termsText);
        termsText.setMovementMethod(new ScrollingMovementMethod());


    }

    public void onTerms1(View view){
        termsText.setText("귀하는 서비스 내에서 적용되는 모든 정책을 준수해야 합니다.\n" +
                "\n" +
                "    Google 서비스의 오용을 삼가시기 바랍니다. 예를 들어 서비스를 방해하거나 Google이 제공하는 인터페이스 및 안내사항 이외의 다른 방법을 사용하여 액세스를 시도하지 않아야 합니다. 귀하는 관련 수출 및 재수출 통제 법규 및 규정 등 오직 법률상 허용되는 범위에서만 Google 서비스를 이용할 수 있습니다. 귀하가 Google 약관이나 정책을 준수하지 않거나 Google이 부정행위 혐의를 조사하고 있는 경우, Google 서비스 제공이 일시 중지 또는 중단될 수 있습니다.\n" +
                "\n" +
                "    Google 서비스를 사용한다고 해서 Google 서비스 또는 액세스하는 콘텐츠의 지적재산권을 소유하게 되는 것은 아닙니다. 콘텐츠 소유자로부터 허가를 받거나 달리 법률에 따라 허용되는 경우를 제외하고, Google 서비스의 콘텐츠를 사용할 수 없습니다. 본 약관은 귀하에게 Google 서비스에 사용된 브랜드나 로고를 사용할 권리를 부여하지 않습니다. Google 서비스에 또는 Google 서비스와 함께 게재된 어떠한 법적 고지도 삭제하거나 감추거나 변경하지 마십시오.\n" +
                "\n" +
                "    Google 서비스는 Google이 소유하지 않은 일부 콘텐츠를 표시할 수 있습니다. 그러한 콘텐츠에 대해서는 제공한 주체가 단독으로 책임지게 됩니다. Google은 콘텐츠의 위법성 여부 또는 Google 정책 위반 여부를 결정하기 위해 검토할 수 있으며, 콘텐츠가 Google 정책 또는 법률에 위반된다고 합리적으로 판단하는 경우 이를 삭제하거나 게시를 거부할 수 있습니다. 그렇다고 반드시 콘텐츠를 검토한다는 의미는 아니므로, 콘텐츠를 검토할 것이라고 간주하지 마십시오.\n" +
                "\n" +
                "    서비스 이용과 관련하여 Google은 귀하에게 서비스 고지, 관리 메시지 및 기타 정보를 발송할 수 있습니다. 귀하는 메시지 수신을 거부할 수 있습니다.\n" +
                "\n" +
                "    일부 Google 서비스는 휴대기기에서 사용할 수 있습니다. 트래픽 또는 보안 관련 법규 준수를 방해하거나 막는 방식으로 서비스를 사용해서는 안 됩니다.");
    }

    public void onTerms2(View view){
        termsText.setText("2국회 측은 녹취록을 근거로 최 씨가 국정의 세세한 부분까지 관여했고 막강한 영향력을 행사했다고 주장하며 역공을 가했다.녹취록에는 김씨가한 십여채 지어가지고 맨 앞, 끝에 큰 거는 VIP…, 맨 끝에가 VIP가 살 동이고라고 박 대통령이 퇴임 후 최씨 등과 함께 거주할 집을 짓기 위해 나눈 대화도 있었다.");
    }

    public void onTerms3(View view){
        termsText.setText("3국회 측은 녹취록을 근거로 최 씨가 국정의 세세한 부분까지 관여했고 막강한 영향력을 행사했다고 주장하며 역공을 가했다.녹취록에는 김씨가한 십여채 지어가지고 맨 앞, 끝에 큰 거는 VIP…, 맨 끝에가 VIP가 살 동이고라고 박 대통령이 퇴임 후 최씨 등과 함께 거주할 집을 짓기 위해 나눈 대화도 있었다.");

    }

    public void onTerms4(View view){
        termsText.setText("4국회 측은 녹취록을 근거로 최 씨가 국정의 세세한 부분까지 관여했고 막강한 영향력을 행사했다고 주장하며 역공을 가했다.녹취록에는 김씨가한 십여채 지어가지고 맨 앞, 끝에 큰 거는 VIP…, 맨 끝에가 VIP가 살 동이고라고 박 대통령이 퇴임 후 최씨 등과 함께 거주할 집을 짓기 위해 나눈 대화도 있었다.");

    }

    public void onTerms5(View view){
        termsText.setText("5국회 측은 녹취록을 근거로 최 씨가 국정의 세세한 부분까지 관여했고 막강한 영향력을 행사했다고 주장하며 역공을 가했다.녹취록에는 김씨가한 십여채 지어가지고 맨 앞, 끝에 큰 거는 VIP…, 맨 끝에가 VIP가 살 동이고라고 박 대통령이 퇴임 후 최씨 등과 함께 거주할 집을 짓기 위해 나눈 대화도 있었다.");

    }

    public void onAgreeBtn(View view){
        Intent intent = new Intent(this, PeopleProfileActivity.class);
        intent.putExtra("type", false);
        startActivity(intent);
    }

}

