package com.example.tacademy.petpp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.model.Member;
import com.example.tacademy.petpp.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo.entities.size.ScreenSize;
import com.miguelbcr.ui.rx_paparazzo.entities.size.SmallSize;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PeopleProfileActivity extends BaseActivity {

    // 상단바 타이틀
    TextView title_text;

    // 스피너 성별
    Spinner p_Spinner_XY, p_Spinner_age;
    ArrayAdapter<CharSequence> p_Spinner_XY_Adapter, p_Spinner_age_Adapter;

    // 프로필 이미지
    ImageView profile_image;
    LinearLayout personLinearLayout;
    EditText name_et, phone_et;

    Button nextBtn;
    Boolean type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // actList에 B를 추가해줍니다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_profile);

        // 상단바
        title_text = (TextView)findViewById(R.id.title_text);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        title_text.setText("주인님이 궁금해요");

        Intent intent = getIntent();
        type = intent.getExtras().getBoolean("type");
        // 프로필 수정이면 이미 저장되있는 데이터를 세팅해준다.
        if(type==true){
            // 프로필 수정이 맞다. -> 데이터 세팅
            nextBtn.setText("완료");
            Toast.makeText(this, "주인님의 프로필을 수정해주세요.", Toast.LENGTH_SHORT).show();
        }else{
            nextBtn.setText("다음");
        }

        p_Spinner_age = (Spinner)findViewById(R.id.p_Spinner_age);
        p_Spinner_XY = (Spinner)findViewById(R.id.p_Spinner_XY);
        Spinner_Select();

        profile_image = (ImageView)findViewById(R.id.profile_image);
        personLinearLayout = (LinearLayout)findViewById(R.id.personLinearLayout);
        name_et = (EditText)findViewById(R.id.name_et);
        phone_et = (EditText)findViewById(R.id.phone_et);
        personLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //closeKeyword(this, );
            }
        });
    }

    // 다음 ========================================================

    // 모두 입력 확인 후 서버에 저장하고 반려견 정보 입력화면으로 이동
    public void onNextBtn(View view){
        if(!isValidate()) return;

        if(Member.getInstance().getKakaoId() == null | Member.getInstance().getKakaoId() == "") {
            Log.getInstance().signLog("카카오톡 아이디와 회원이름이 들어오지 않았습니다. 확인 부탁드립니다.");
        }else{
            // 잘들어왔는지 확인과정
            Log.getInstance().signLog("프로필 화면에서 카카오톡 아이다 :" + Member.getInstance().getKakaoId());
            Log.getInstance().signLog("프로필 화면에서 회원이름 :" + Member.getInstance().getName());
            Log.getInstance().signLog("프로필 화면에서 이미지 url :" + Member.getInstance().getImageURL());
            Log.getInstance().signLog("프로필 화면에서 회원이름 :" + Member.getInstance().getMobile());


            if(type == true){
                Toast.makeText(PeopleProfileActivity.this, "완료되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Intent intent = new Intent(this, DogProfileActivity.class);
                intent.putExtra("type", false);
                startActivity(intent);
            }
        }
    }

    // 정보 비어있는지 확인
    public boolean isValidate(){
        if(TextUtils.isEmpty(name_et.getText().toString())){   // TextUtils.isEmpty : 비어있는지 아닌지 -> 널이나 공백 -> 이 경우는 공백
            name_et.setError("이름을 입력하세요");
            return false;
        } else if(TextUtils.isEmpty(phone_et.getText().toString())){
            phone_et.setError("핸드폰 번호를 입력하세요");
            return false;
        }else {
            Member.getInstance().setName(name_et.getText().toString());
            Member.getInstance().setMobile(phone_et.getText().toString());
            name_et.setError(null);
            phone_et.setError(null);
        }
        return true;
    }

    // ============================= 스피너에 어댑터 달기 ================
    public void Spinner_Select(){
        // 나이 선택
        p_Spinner_age_Adapter = ArrayAdapter.createFromResource(this, R.array.p_array_list_age, android.R.layout.simple_spinner_item);
        p_Spinner_age_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        p_Spinner_age.setAdapter(p_Spinner_age_Adapter);
        p_Spinner_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "나이 The planet is " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                Member.getInstance().setAge(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 성별 선택
        p_Spinner_XY_Adapter = ArrayAdapter.createFromResource(this, R.array.p_array_list_xy, android.R.layout.simple_spinner_item);
        p_Spinner_XY_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        p_Spinner_XY.setAdapter(p_Spinner_XY_Adapter);
        p_Spinner_XY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "성별The planet is " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                Member.getInstance().setXY(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // ============================== 카메라 =============================
    SweetAlertDialog alert, alert2;
    String urlString = null;

    // 사진 선택 or 사진 삭제
    public void onPImageView(View view){
        if( urlString==null ){
            onPhoto(view);
        }else{
            Log.getInstance().signLog("초기 urlString : " + urlString);
            alert2 = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("사진삭제")
                    .setContentText("사진을 삭제시키고 다시 선택하겠습니까?")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener(){
                        public void onClick(SweetAlertDialog sDialog) {
                            fileDelete(urlString);
                        }
                    });
            alert2.show();
        }
    }

    // 카메라 or 갤러리
    public void onPhoto(View view){
        alert =
                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("사진선택")
                        .setContentText("사진을 선택할 방법을 고르세요!!")
                        .setConfirmText("카메라")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                onCamera();
                            }
                        })
                        .setCancelText("갤러리")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                onGallery();
                            }
                        });
        alert.setCancelable(true);
        alert.show();
    }

    // 카메라 - 크롭작업을 하기 위해 옵션 설정 (편집)
    public void onCamera(){
        // 크롭작업을 하기 위해 옵션 설정 (편집)
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.setMaxBitmapSize(1024*1024*2);    // 2MB 언더

        RxPaparazzo.takeImage(this)
                .size(new SmallSize())                     // 사이즈 new SmallSize() (SmallSize, ScreenSize, OriginalSize, CustomMaxSize)
                .crop(options)                              // 편집
                .useInternalStorage()                       // 내부저장 (안쓰면 외부 공용공간에 앱이름으로 생성됨)
                .usingCamera()                              // 카메라 사용
                .subscribeOn(Schedulers.io())               // IO
                .observeOn(AndroidSchedulers.mainThread())  //스레드 설정
                .subscribe(response -> {                    // 결과 처리
                    // See response.resultCode() doc
                    if (response.resultCode() != RESULT_OK) {
                        //response.targetUI().showUserCanceled();
                        return;
                    }
                    loadImage(response.data());
                });
    }

    // 갤러리 - 크롭작업을 하기 위해 옵션 설정 (편집)
    public void onGallery(){
        // 크롭작업을 하기 위해 옵션 설정 (편집)
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.setMaxBitmapSize(1024*1024*2);    // 2MB 언더

        RxPaparazzo.takeImage(this)
                .size(new ScreenSize())                     // 사이즈 (SmallSize, ScreenSize, OriginalSize, CustomMaxSize)
                .crop(options)                              // 편집
                .useInternalStorage()                       // 내부저장 (안쓰면 외부 공용공간에 앱이름으로 생성됨)
                .usingGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    // See response.resultCode() doc
                    if (response.resultCode() != RESULT_OK) {
                        //response.targetUI().showUserCanceled();
                        return;
                    }
                    Log.getInstance().signLog( response.data());
                    loadImage(response.data());
                });
    }

    // 이미지뷰에 이미지를 세팅
    public void loadImage(String path){
        alert.dismissWithAnimation();
        // 이미지뷰에 이미지를 세팅
        // /data/user/0/com.example.tacademy.photoprocessing/files/PhotoProcessing/IMG-19012017_044511_233.jpeg
        String url = "file://" + path;
        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).setIndicatorsEnabled(true);
        Picasso.with(this).invalidate(url);
        Picasso.with(this).load(url).into(profile_image);

        fileUpload(path);
    }

    // ====================================


    // 파일 서버에 업로드
    public void fileUpload(String url){

        // 내 프로필 사진이 등록되는 최종 경로
        Uri file = Uri.fromFile(new File(url));
        // 기둥에 가지 등록
        urlString = "image/"+file.getLastPathSegment();
        StorageReference riversRef = storageRef.child(urlString);
        // 업로드
        UploadTask uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // 실패 -> 재시도를 하겠끔 유도!!
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.getInstance().signLog("downloadUrl : " + downloadUrl.toString());
                Member.getInstance().setImageURL(downloadUrl.toString());
                // downloadUrl.toString() => 프로필 정보로 업로드!
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                // 진행율!!
                float rate = (taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount())*100.0f;
                Log.getInstance().signLog("진행율 : " + rate);
            }
        });
    }

    public void fileDelete(String url){

        StorageReference desertRef = storageRef.child(url);
        // StorageReference desertRef = storageRef.getReferenceFromUrl("");
        Log.getInstance().signLog( "fileDelete(); 호출");
        Log.getInstance().signLog( "desertRef" + url);

        // Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void aVoid) {
                profile_image.setImageDrawable(getResources().getDrawable(R.mipmap.profile));
                urlString = null;
                alert2.dismissWithAnimation();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }

}
