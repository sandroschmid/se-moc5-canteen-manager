package com.example.canteenchecker.canteenmanager.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.App;
import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.app.entity.Rating;
import com.example.canteenchecker.canteenmanager.app.event.BaseRequestResultEvent;
import com.example.canteenchecker.canteenmanager.app.event.EventReceiver;
import com.example.canteenchecker.canteenmanager.app.event.RatingDeletedEvent;
import com.example.canteenchecker.canteenmanager.app.request.DeleteAdminCanteenRatingRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author sschmid
 */
public final class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.ViewHolder> {

  private final RatingDeletedEvent ratingDeletedEvent = App.getInstance().getEventManager().getRatingDeletedEvent();

  private EventReceiver<BaseRequestResultEvent.RequestResult<String>> ratingDeletedEventReceiver;
  private ArrayList<Rating> ratings;

  public RatingsAdapter(final Context context) {
    this(context, new ArrayList<Rating>());
  }

  public RatingsAdapter(final Context context, final ArrayList<Rating> ratings) {
    this.ratings = ratings;

    ratingDeletedEventReceiver = new EventReceiver<BaseRequestResultEvent.RequestResult<String>>() {
      @Override
      public void onNewEvent(final BaseRequestResultEvent.RequestResult<String> result) {
        if (result.isSuccessful()) {
          remove(result.getData());
        } else {
          Toast.makeText(
              context,
              R.string.app_error_delete_rating_failure,
              Toast.LENGTH_SHORT
          ).show();
        }
      }
    };

    ratingDeletedEvent.register(ratingDeletedEventReceiver);
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

  public void destroy() {
    ratingDeletedEvent.unregister(ratingDeletedEventReceiver);
  }

  public void setRatings(final ArrayList<Rating> ratings) {
    this.ratings = ratings;
    notifyDataSetChanged();
  }

  public void removeAll() {
    ratings.clear();
    notifyDataSetChanged();
  }

  private void remove(final String id) {
    for (int i = 0; i < ratings.size(); i++) {
      if (ratings.get(i).getId().equals(id)) {
        ratings.remove(i);
        notifyItemRemoved(i);
        return;
      }
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
      new DeleteAdminCanteenRatingRequest(context, (String) btnDelete.getTag()).send();
    }
  }
}
