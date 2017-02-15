package com.example.tacademy.petpp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.model.Member;
import com.example.tacademy.petpp.ui.main.act.MainTLActivity;
import com.example.tacademy.petpp.util.Log;
import com.example.tacademy.petpp.util.StorageHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class DogProfileActivity extends BaseActivity {

    // 상단바 타이틀
    TextView title_text;
    Button nextBtn, jumpBtn;

    Spinner dog_Spinner_age, dog_Spinner_Type, dog_Spinner_XY, dog_Spinner_kg, dog_Spinner_yn;
    ArrayAdapter<CharSequence> dog_Spinner_age_Adapter, dog_Spinner_Type_Adapter, dog_Spinner_XY_Adapter, dog_Spinner_kg_Adapter, dog_Spinner_yn_Adapter;

    ImageView dog_profile_image;
    Boolean type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // actList에 B를 추가해줍니다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);

        // 상단바
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("반려견을 소개해요");
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setText("완료");

        jumpBtn = (Button)findViewById(R.id.jumpBtn);

        Intent intent = getIntent();
        type = intent.getExtras().getBoolean("type");
        // 프로필 수정이면 이미 저장되있는 데이터를 세팅해준다.
        if(type==true){
            // 프로필 수정이 맞다. -> 데이터 세팅
            jumpBtn.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "반려견의 프로필을 수정해주세요.", Toast.LENGTH_SHORT).show();
        }

        dog_Spinner_age = (Spinner)findViewById(R.id.dog_Spinner_age);
        dog_Spinner_Type = (Spinner)findViewById(R.id.dog_Spinner_Type);
        dog_Spinner_XY = (Spinner)findViewById(R.id.dog_Spinner_XY);
        dog_Spinner_kg = (Spinner)findViewById(R.id.dog_Spinner_kg);
        dog_Spinner_yn = (Spinner)findViewById(R.id.dog_Spinner_yn);
        dog_Spinner_Select();


        dog_profile_image = (ImageView)findViewById(R.id.dog_profile_image);
    }

    // 다음 ========================================================
    // 견주 프로필작성하고 다음 누를 때에 사람에 대한 db는 저장해야함. 건너띄기하면 ... 모두 저장안된다..
    // 견주 프로필 다음 : 견주 디비 저장, 반려견 프로필 완료 : 반려견 디비 저장, 반려견 프로필 건너뛰기 : 타임라인으로 이동
    // 아니면 건너뛰기 할 때 사람에 대한 정보만 디비에 저장.

    // 모두 입력 확인 후 서버에 저장하고 메인 화면으로 이동
    public void onNextBtn(View view){
        onUserSaved(view);
    }

    // 서버에 저장하지 않고 메인 화면으로 이동
    public void onTLNext(View view){
        Toast.makeText(DogProfileActivity.this, "마이페이지에서 프로필을 수정할 수 있습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainTLActivity.class);
        startActivity(intent);
        actFinish();
    }


    // 회원 정보 디비에 저장
    public void onUserSaved(View view){

        // 1. 로딩
        showProgress("회원가입중...");
        // 2. 디비에 회원정보 입력

        // 회원정보 생성
        String kakaoId  = Member.getInstance().getKakaoId();

        // 디비 입력
        databaseReference.child("users").child(kakaoId).setValue(Member.getInstance())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        hideProgress();
                        if(task.isSuccessful()){
                            // 디비 저장 완료. 메인화면으로 이동
                            StorageHelper.getInstance().setBoolean(DogProfileActivity.this, "LOGIN", true);
                            Log.getInstance().signLog("회원정보 입력 모두 완료, 메인화면으로 이동");

                            if(type == true){
                                DogProfileActivity.this.finish();
                            }else {
                                Intent intent = new Intent(DogProfileActivity.this, MainTLActivity.class);
                                startActivity(intent);
                                actFinish();
                            }
                        }
                        else{
                            // 회원정보 디비 저장 실패
                            Toast.makeText(DogProfileActivity.this, "서버에 오류가 있습니다. 다시 실행해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                            Log.getInstance().signLog("회원정보 디비 저장 실패");
                            actFinish();
                        }
                    }
                });
    }

    // ============================= 스피너에 어댑터 달기 ================
    public void dog_Spinner_Select(){
        // 나이 선택
        dog_Spinner_age_Adapter = ArrayAdapter.createFromResource(this, R.array.dog_array_list_age, android.R.layout.simple_spinner_item);
        dog_Spinner_age_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        dog_Spinner_age.setAdapter(dog_Spinner_age_Adapter);
        dog_Spinner_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "나이 The planet is " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 견종 선택
        dog_Spinner_Type_Adapter = ArrayAdapter.createFromResource(this, R.array.dog_array_list_type, android.R.layout.simple_spinner_item);
        dog_Spinner_Type_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        dog_Spinner_Type.setAdapter(dog_Spinner_Type_Adapter);
        dog_Spinner_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "성별The planet is " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 성별 선택
        dog_Spinner_XY_Adapter = ArrayAdapter.createFromResource(this, R.array.dog_array_list_xy, android.R.layout.simple_spinner_item);
        dog_Spinner_XY_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        dog_Spinner_XY.setAdapter(dog_Spinner_XY_Adapter);
        dog_Spinner_XY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "성별The planet is " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 무게 선택
        dog_Spinner_kg_Adapter = ArrayAdapter.createFromResource(this, R.array.dog_array_list_kg, android.R.layout.simple_spinner_item);
        dog_Spinner_kg_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        dog_Spinner_kg.setAdapter(dog_Spinner_kg_Adapter);
        dog_Spinner_kg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "성별The planet is " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 중성화 여부 선택
        dog_Spinner_yn_Adapter = ArrayAdapter.createFromResource(this, R.array.dog_array_list_yn, android.R.layout.simple_spinner_item);
        dog_Spinner_yn_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        dog_Spinner_yn.setAdapter(dog_Spinner_yn_Adapter);
        dog_Spinner_yn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "성별The planet is " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
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
    public void onDImageView(View view){
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
        Picasso.with(this).load(url).into(dog_profile_image);

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
                dog_profile_image.setImageDrawable(getResources().getDrawable(R.mipmap.profile));
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

    // ========================================================================================

}
