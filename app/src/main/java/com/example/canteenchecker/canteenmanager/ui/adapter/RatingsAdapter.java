package com.example.canteenchecker.canteenmanager.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Rating;
import com.example.canteenchecker.canteenmanager.app.request.DeleteAdminCanteenRatingRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author sschmid
 */
public final class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.ViewHolder> {
  private ArrayList<Rating> ratings;

  private ProgressDialog progressDialog;

  public RatingsAdapter(final Context context) {
    this(context, new ArrayList<Rating>());
  }

  public RatingsAdapter(final Context context, final ArrayList<Rating> ratings) {
    this.ratings = ratings;
  }

  @Override
  public RatingsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
        R.layout.list_item_review,
        parent,
        false
    ));
  }

  @Override
  public void onBindViewHolder(final RatingsAdapter.ViewHolder holder, final int position) {
    holder.bind(ratings.get(position));
  }

  @Override
  public int getItemCount() {
    return ratings == null ? 0 : ratings.size();
  }

  public void setRatings(final ArrayList<Rating> ratings) {
    this.ratings = ratings;
    notifyDataSetChanged();
  }

  public void removeAll() {
    ratings.clear();
    notifyDataSetChanged();
  }

  public void remove(final String id) {
    for (int i = 0; i < ratings.size(); i++) {
      if (ratings.get(i).getId().equals(id)) {
        ratings.remove(i);
        notifyItemRemoved(i);
        return;
      }
    }
  }

  public boolean isEmpty() {
    return ratings == null || ratings.isEmpty();
  }

  public void dismissLoader() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

    private final AppCompatRatingBar rbRating;
    private final AppCompatTextView tvDescription;
    private final AppCompatTextView tvUser;
    private final AppCompatTextView tvDate;
    private final View btnDelete;

    public ViewHolder(final View view) {
      super(view);
      context = view.getContext();
      rbRating = view.findViewById(R.id.rbRating);
      tvDescription = view.findViewById(R.id.tvDescription);
      tvUser = view.findViewById(R.id.tvUser);
      tvDate = view.findViewById(R.id.tvDate);
      btnDelete = view.findViewById(R.id.btnDelete);
      btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
          delete();
        }
      });
    }

    private void bind(final Rating rating) {
      rbRating.setRating(rating.getRating());
      if (rating.hasDescription()) {
        tvDescription.setText(rating.getDescription());
        tvDescription.setVisibility(View.VISIBLE);
      } else {
        tvDescription.setVisibility(View.GONE);
      }
      tvUser.setText(rating.getUserName());
      tvDate.setText(dateFormat.format(rating.getDate()));
      btnDelete.setTag(rating.getId());
    }

    private void delete() {
      new AlertDialog.Builder(context)
          .setTitle(R.string.app_rating_confirm_delete_title)
          .setMessage(R.string.app_rating_confirm_delete_text)
          .setIcon(android.R.drawable.ic_dialog_alert)
          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
              progressDialog = ProgressDialog.show(
                  context,
                  "",
                  context.getString(R.string.app_splash_loading),
                  true
              );

              new DeleteAdminCanteenRatingRequest(context, (String) btnDelete.getTag()).send();
            }
          })
          .setNegativeButton(android.R.string.no, null)
          .show();
    }
  }
}
