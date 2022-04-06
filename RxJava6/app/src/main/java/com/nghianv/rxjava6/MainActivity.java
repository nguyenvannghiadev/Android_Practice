package com.nghianv.rxjava6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.nghianv.rxjava6.adapter.TicketsAdapter;
import com.nghianv.rxjava6.network.ApiClient;
import com.nghianv.rxjava6.network.ApiService;
import com.nghianv.rxjava6.network.model.Price;
import com.nghianv.rxjava6.network.model.Ticket;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements TicketsAdapter.TicketsAdapterListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String FROM = "DEL";
	private static final String TO = "HYD";

	private CompositeDisposable compositeDisposable = new CompositeDisposable();
	private Unbinder unbinder;

	private ApiService apiService;
	private TicketsAdapter mAdapter;
	private ArrayList<Ticket> ticketsList = new ArrayList<>();

	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		unbinder = ButterKnife.bind(this);
		//
		apiService = ApiClient.getClient().create(ApiService.class);
		//
		mAdapter = new TicketsAdapter(this, ticketsList, this);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(1, dpToPx(5), true));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(mAdapter);
		//
		ConnectableObservable<List<Ticket>> ticketsObervable = getTickets(FROM, TO).replay();

		/*
		 * Fetch all tickets first
		 * Observable emits List<Ticket> at once
		 * All the items will be added to RecyclerView
		 */

		compositeDisposable.add(
				ticketsObervable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribeWith(new DisposableObserver<List<Ticket>>() {

							@Override
							public void onNext(List<Ticket> tickets) {
								// Refeshing list
								ticketsList.clear();
								ticketsList.addAll(tickets);
								mAdapter.notifyDataSetChanged();
							}

							@Override
							public void onError(Throwable e) {
								Log.d(TAG, "ShowError:" + e.getMessage());
							}

							@Override
							public void onComplete() {

							}
						})
		);
		//

		/*
		 * Fetching individual ticket price
		 * First FlapMap converts single List<Ticket> to multiple emissions
		 * Second FlatMap makes HTTP call on each Ticket emission
		 */

		compositeDisposable.add(
				ticketsObervable
						.subscribeOn(Schedulers.io())
						/*
						 * Converting List<Ticket> emission to single Ticket emission
						 */
						.flatMap(new Function<List<Ticket>, ObservableSource<Ticket>>() {
							         @Override
							         public ObservableSource<Ticket> apply(List<Ticket> tickets) throws Exception {
								         return Observable.fromIterable(tickets);
							         }
						         }
						)
						/*
						 * Fetching price on each Ticket emission
						 */
						.flatMap(new Function<Ticket, ObservableSource<Ticket>>() {
							@Override
							public ObservableSource<Ticket> apply(Ticket ticket) throws Exception {
								return getPriceObservable(ticket);
							}
						})
						.observeOn(AndroidSchedulers.mainThread())
						.subscribeWith(new DisposableObserver<Ticket>() {
							@Override
							public void onNext(Ticket ticket) {
								int position = ticketsList.indexOf(ticket);

								if (position == -1) {
									return;
								}

								ticketsList.set(position, ticket);
								mAdapter.notifyItemChanged(position);

							}

							@Override
							public void onError(Throwable e) {
								showError(e);
							}

							@Override
							public void onComplete() {

							}
						}));

		// Calling connect to start emission
		ticketsObervable.connect();

	}

	/*
	 * Making Retrofit call to fetch all tickets
	 */
	private Observable<List<Ticket>> getTickets(String from, String to) {
		return apiService.searchTickets(from, to)
				.toObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	/**
	 * Making Retrofit call to get single ticket price
	 * get price HTTP call returns Price object, but
	 * map() operator is used to change the return type to Ticket
	 */
	private Observable<Ticket> getPriceObservable(final Ticket ticket) {
		return apiService.getPrice(ticket.getFlightNumber(), ticket.getFrom(), ticket.getTo())
				.toObservable()
				.subscribeOn(Schedulers.io())
				.map(new Function<Price, Ticket>() {
					@Override
					public Ticket apply(Price price) throws Exception {
						ticket.setPrice(price);
						return ticket;
					}
				})
				.observeOn(AndroidSchedulers.mainThread());
	}

	/**
	 * Snackbar shows observer error
	 */
	private void showError(Throwable throwable) {
		Log.d(TAG, "ShowError:" + throwable.getMessage());
	}


	public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

		private int spanCount;
		private int spacing;
		private boolean includeEdge;

		public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
			this.spanCount = spanCount;
			this.spacing = spacing;
			this.includeEdge = includeEdge;
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			int position = parent.getChildAdapterPosition(view); // item position
			int column = position % spanCount; // item column

			if (includeEdge) {
				outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
				outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

				if (position < spanCount) { // top edge
					outRect.top = spacing;
				}
				outRect.bottom = spacing; // item bottom
			} else {
				outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
				outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
				if (position >= spanCount) {
					outRect.top = spacing; // item top
				}
			}
		}
	}

	private int dpToPx(int dp) {
		Resources r = getResources();
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
	}

	@Override
	public void onTicketSeletedClick(Ticket ticket) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		compositeDisposable.dispose();
		unbinder.unbind();
	}
}