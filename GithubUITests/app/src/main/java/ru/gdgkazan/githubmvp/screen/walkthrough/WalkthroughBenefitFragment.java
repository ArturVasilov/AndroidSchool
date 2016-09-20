package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Benefit;

/**
 * @author Artur Vasilov
 */
public class WalkthroughBenefitFragment extends Fragment {

    private static final String BENEFIT_KEY = "benefit";

    @BindView(R.id.benefitIcon)
    ImageView mBenefitIcon;

    @BindView(R.id.benefitText)
    TextView mBenefitText;

    private Benefit mBenefit;

    @NonNull
    public static WalkthroughBenefitFragment create(@NonNull Benefit benefit) {
        Bundle bundle = new Bundle();
        bundle.putString(BENEFIT_KEY, benefit.name());
        WalkthroughBenefitFragment fragment = new WalkthroughBenefitFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String benefit = getArguments().getString(BENEFIT_KEY, Benefit.WORK_TOGETHER.name());
        mBenefit = Benefit.valueOf(benefit);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_benefit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBenefitIcon.setImageResource(mBenefit.getDrawableId());
        mBenefitText.setText(mBenefit.getTextId());
    }
}
