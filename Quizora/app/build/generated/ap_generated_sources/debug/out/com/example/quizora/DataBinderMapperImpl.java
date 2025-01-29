package com.example.quizora;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.quizora.databinding.ActivityPhase1BindingImpl;
import com.example.quizora.databinding.ActivityPhase2BindingImpl;
import com.example.quizora.databinding.ActivityPhase3BindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYPHASE1 = 1;

  private static final int LAYOUT_ACTIVITYPHASE2 = 2;

  private static final int LAYOUT_ACTIVITYPHASE3 = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.quizora.R.layout.activity_phase1, LAYOUT_ACTIVITYPHASE1);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.quizora.R.layout.activity_phase2, LAYOUT_ACTIVITYPHASE2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.quizora.R.layout.activity_phase3, LAYOUT_ACTIVITYPHASE3);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYPHASE1: {
          if ("layout/activity_phase1_0".equals(tag)) {
            return new ActivityPhase1BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_phase1 is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPHASE2: {
          if ("layout/activity_phase2_0".equals(tag)) {
            return new ActivityPhase2BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_phase2 is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPHASE3: {
          if ("layout/activity_phase3_0".equals(tag)) {
            return new ActivityPhase3BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_phase3 is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/activity_phase1_0", com.example.quizora.R.layout.activity_phase1);
      sKeys.put("layout/activity_phase2_0", com.example.quizora.R.layout.activity_phase2);
      sKeys.put("layout/activity_phase3_0", com.example.quizora.R.layout.activity_phase3);
    }
  }
}
