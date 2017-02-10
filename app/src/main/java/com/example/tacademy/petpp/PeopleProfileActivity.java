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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.model.Member;
import com.example.tacademy.petpp.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
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

    // 스피너 성별
    Spinner XYSpinner;
    ArrayAdapter<CharSequence> XYAdapter;

    // 프로필 이미지
    ImageView profile_image;
    LinearLayout personLinearLayout;
    EditText name_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // actList에 B를 추가해줍니다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_profile);

        XYSpinner = (Spinner)findViewById(R.id.XYSpinner);
        XYSelect();

        profile_image = (ImageView)findViewById(R.id.profile_image);
        personLinearLayout = (LinearLayout)findViewById(R.id.personLinearLayout);
        name_et = (EditText)findViewById(R.id.name_et);
        personLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //closeKeyword(this, );
            }
        });
    }

    // 성별 선택
    public void XYSelect(){
        XYAdapter = ArrayAdapter.createFromResource(this, R.array.xy_array_list, android.R.layout.simple_spinner_item);
        XYAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        XYSpinner.setAdapter(XYAdapter);
        XYSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(parent.getContext(), "The planet is " +
//                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
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

    // =======================================================================================


    FirebaseStorage storage = FirebaseStorage.getInstance();
    // 나무 기둥의 주소
    StorageReference storageRef = storage.getReferenceFromUrl("gs://petpal-473d4.appspot.com");

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

    // 다음 ========================================================

    // 모두 입력 확인 후 서버에 저장하고 반려견 정보 입력화면으로 이동
    public void onPProfileNext(View view){
        if(!isValidate()) return;

        if(Member.getInstance().getKakaoId() == null | Member.getInstance().getKakaoId() == "") {
            Log.getInstance().signLog("카카오톡 아이디와 회원이름이 들어오지 않았습니다. 확인 부탁드립니다.");
        }else{
            // 잘들어왔는지 확인과정
            Log.getInstance().signLog("프로필 화면에서 카카오톡 아이다 :" + Member.getInstance().getKakaoId());
            Log.getInstance().signLog("프로필 화면에서 회원이름 :" + Member.getInstance().getName());

            Intent intent = new Intent(this, DogProfileActivity.class);
            startActivity(intent);
        }
    }

    // 이름 비어있는지 확인
    public boolean isValidate(){
        if(TextUtils.isEmpty(name_et.getText().toString())){   // TextUtils.isEmpty : 비어있는지 아닌지 -> 널이나 공백 -> 이 경우는 공백
            name_et.setError("이름을 입력하세요");
            return false;
        }else {
            Member.getInstance().setName(name_et.getText().toString());
            name_et.setError(null);
        }
        return true;
    }
}
