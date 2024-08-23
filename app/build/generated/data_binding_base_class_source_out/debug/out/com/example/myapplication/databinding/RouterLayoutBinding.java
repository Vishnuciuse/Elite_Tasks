// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class RouterLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final View conBottomMainView;

  @NonNull
  public final View conEndMainView;

  @NonNull
  public final View conTopMainView;

  @NonNull
  public final ConstraintLayout connectionMainView;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final RecyclerView devicesConnectedRV;

  @NonNull
  public final ImageView routerImageView;

  @NonNull
  public final TextView routerNameTV;

  private RouterLayoutBinding(@NonNull ConstraintLayout rootView, @NonNull View conBottomMainView,
      @NonNull View conEndMainView, @NonNull View conTopMainView,
      @NonNull ConstraintLayout connectionMainView, @NonNull ConstraintLayout constraintLayout,
      @NonNull RecyclerView devicesConnectedRV, @NonNull ImageView routerImageView,
      @NonNull TextView routerNameTV) {
    this.rootView = rootView;
    this.conBottomMainView = conBottomMainView;
    this.conEndMainView = conEndMainView;
    this.conTopMainView = conTopMainView;
    this.connectionMainView = connectionMainView;
    this.constraintLayout = constraintLayout;
    this.devicesConnectedRV = devicesConnectedRV;
    this.routerImageView = routerImageView;
    this.routerNameTV = routerNameTV;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RouterLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RouterLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.router_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RouterLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.conBottomMainView;
      View conBottomMainView = ViewBindings.findChildViewById(rootView, id);
      if (conBottomMainView == null) {
        break missingId;
      }

      id = R.id.conEndMainView;
      View conEndMainView = ViewBindings.findChildViewById(rootView, id);
      if (conEndMainView == null) {
        break missingId;
      }

      id = R.id.conTopMainView;
      View conTopMainView = ViewBindings.findChildViewById(rootView, id);
      if (conTopMainView == null) {
        break missingId;
      }

      id = R.id.connectionMainView;
      ConstraintLayout connectionMainView = ViewBindings.findChildViewById(rootView, id);
      if (connectionMainView == null) {
        break missingId;
      }

      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.devicesConnectedRV;
      RecyclerView devicesConnectedRV = ViewBindings.findChildViewById(rootView, id);
      if (devicesConnectedRV == null) {
        break missingId;
      }

      id = R.id.routerImageView;
      ImageView routerImageView = ViewBindings.findChildViewById(rootView, id);
      if (routerImageView == null) {
        break missingId;
      }

      id = R.id.routerNameTV;
      TextView routerNameTV = ViewBindings.findChildViewById(rootView, id);
      if (routerNameTV == null) {
        break missingId;
      }

      return new RouterLayoutBinding((ConstraintLayout) rootView, conBottomMainView, conEndMainView,
          conTopMainView, connectionMainView, constraintLayout, devicesConnectedRV, routerImageView,
          routerNameTV);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
