package com.lihang.mycamera.ui.mycamera.activity;

import android.view.View;
import android.widget.Toast;

import com.lihang.mycamera.R;
import com.lihang.mycamera.base.BaseActivity;
import com.lihang.mycamera.databinding.ActivityMyCameraBinding;
import com.lihang.mycamera.ui.mycamera.OnCaptureData;


/**
 * Created by leo
 * on 2020/3/13.
 */
public class MyCameraActivity extends BaseActivity<ActivityMyCameraBinding> implements OnCaptureData {
    int flashFlag;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_camera;
    }


    @Override
    protected void onStart() {
        super.onStart();
        binding.imageTake.setEnabled(true);
        binding.imageSwitch.setEnabled(true);
    }

    @Override
    protected void processLogic() {
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_take:
                binding.imageTake.setEnabled(false);
                binding.imageSwitch.setEnabled(false);
                binding.cameraView.takePicture(this);
                break;

            case R.id.image_switch:
                //切换摄像头
                binding.cameraView.switchCamera();
                if (binding.cameraView.isFront()) {
                    binding.imageFlash.setVisibility(View.GONE);
                } else {
                    binding.imageFlash.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.image_flash:
                flashFlag++;
                binding.cameraView.switchFlash(flashFlag);
                if (flashFlag % 3 == 0) {
                    binding.imageFlash.setImageResource(R.mipmap.flash_a);
                } else if (flashFlag % 3 == 1) {
                    binding.imageFlash.setImageResource(R.mipmap.flash_on);
                } else {
                    binding.imageFlash.setImageResource(R.mipmap.flash_off);
                }
                break;
        }
    }

    @Override
    public void onCapture(boolean success, String path) {
        if (success) {
            ResultActivity.startActivity(MyCameraActivity.this, path);
            overridePendingTransition(R.anim.scale_activity_go, R.anim.scale_activity_come);
        } else {
            Toast.makeText(this, "拍照异常", Toast.LENGTH_SHORT).show();
        }
    }


}
