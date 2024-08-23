// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView bitmapImageView;

  @NonNull
  public final TextView checkTV1;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final RecyclerView mainRecyclerView;

  @NonNull
  public final Button showBn;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView bitmapImageView, @NonNull TextView checkTV1,
      @NonNull ConstraintLayout main, @NonNull RecyclerView mainRecyclerView,
      @NonNull Button showBn) {
    this.rootView = rootView;
    this.bitmapImageView = bitmapImageView;
    this.checkTV1 = checkTV1;
    this.main = main;
    this.mainRecyclerView = mainRecyclerView;
    this.showBn = showBn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bitmapImageView;
      ImageView bitmapImageView = ViewBindings.findChildViewById(rootView, id);
      if (bitmapImageView == null) {
        break missingId;
      }

      id = R.id.checkTV1;
      TextView checkTV1 = ViewBindings.findChildViewById(rootView, id);
      if (checkTV1 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.mainRecyclerView;
      RecyclerView mainRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (mainRecyclerView == null) {
        break missingId;
      }

      id = R.id.showBn;
      Button showBn = ViewBindings.findChildViewById(rootView, id);
      if (showBn == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, bitmapImageView, checkTV1, main,
          mainRecyclerView, showBn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
