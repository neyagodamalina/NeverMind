package ru.neyagodamalina.nevermind.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class CommonActivity extends AppCompatActivity {
    private static Fragment fragment;

    public void setCurrentFragment(Fragment fragment){
        this.fragment = fragment;
    }

    public Fragment getCurrentFragment(){
        return this.fragment;
    }
}
