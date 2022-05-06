package com.example.mireamarket.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.mireamarket.R;
import com.example.mireamarket.SecondActivity;
import com.example.mireamarket.databinding.FragmentNotificationsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private String checker="";
    private Uri imageUri;
    private CircleImageView profileImView;
    private EditText name, surname, group;
    private TextView saveProfile, closeProfile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        profileImView=root.findViewById(R.id.account_image);
        name=root.findViewById(R.id.fill_name);
        surname=root.findViewById(R.id.fill_surname);
        group=root.findViewById(R.id.fill_group);
        saveProfile=root.findViewById(R.id.save_account_tw);
        closeProfile=root.findViewById(R.id.close_account_tw);

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){
                    return;
                }
                else{
                    updateOnlyUserInfo();
                }
            }
        });

        profileImView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker="clicked";
                CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                        .start(new SecondActivity());


            }
        });





        return root;


    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String,Object> usermap=new HashMap<>();
        usermap.put("name",name.getText().toString());
        usermap.put("surname",surname.getText().toString());
        usermap.put("group",group.getText().toString());
        ref.child(new)
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}