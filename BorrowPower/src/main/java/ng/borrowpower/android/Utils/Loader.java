package ng.borrowpower.android.Utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ng.borrowpower.android.R;

public class Loader extends DialogFragment {
    View view;
    ConstraintLayout constraintLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.SheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.loader, container, false);
        removeBg(constraintLayout);
        return view;
    }

    private void removeBg(ConstraintLayout constraintLayout) {
        constraintLayout = view.findViewById(R.id.container);
        constraintLayout.setBackground(null);
    }
}
