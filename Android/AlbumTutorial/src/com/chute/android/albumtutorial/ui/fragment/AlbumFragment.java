package com.chute.android.albumtutorial.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chute.android.albumtutorial.Constants;
import com.chute.android.albumtutorial.R;
import com.chute.sdk.v2.api.album.GCAlbums;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class AlbumFragment extends Fragment {

  public static final String TAG = AlbumFragment.class.getSimpleName();

  public Context context;
  private AlbumModel album = new AlbumModel();

  private TextView id;
  private TextView links;
  private TextView createdAt;
  private TextView updatedAt;
  private TextView shortcut;
  private TextView description;
  private TextView user;
  private TextView counters;

  private CheckBox checkBoxModerateMedia;
  private CheckBox checkBoxModerateComments;
  private EditText editTextName;

  public void setAlbumModel(AlbumModel album) {
    this.album = album;
    init();
  }

  private void init() {
    id.setText(getResources().getString(R.string.txt_id) + " "
        + album.getId());
    links.setText(getResources().getString(R.string.txt_links) + " "
        + album.getLinks());
    createdAt.setText(getResources().getString(R.string.txt_created_at)
        + " " + album.getCreatedAt());
    updatedAt.setText(getResources().getString(R.string.txt_updated_at)
        + " " + album.getUpdatedAt());
    shortcut.setText(getResources().getString(R.string.txt_shortcut) + " "
        + album.getShortcut());
    if (!TextUtils.isEmpty(album.getName())) {
      editTextName.setText(album.getName());
    }
    description.setText(getResources().getString(R.string.txt_description)
        + " " + album.getDescription());
    user.setText(getResources().getString(R.string.txt_user) + " "
        + album.getUser().toString());
    if (album.isModerateComments() == true) {
      checkBoxModerateComments.setChecked(true);
    } else {
      checkBoxModerateComments.setChecked(false);
    }
    if (album.isModerateMedia() == true) {
      checkBoxModerateMedia.setChecked(true);
    } else {
      checkBoxModerateMedia.setChecked(false);
    }
    if (album.getCounters() != null) {
      counters.setText(getResources().getString(R.string.txt_counters)
          + " " + album.getCounters().toString());
    }

  }

  public AlbumModel collectAlbumData() {
    album.setId(Constants.TEST_ALBUM_ID);
    album.setName(editTextName.getText().toString());
    album.setModerateComments(checkBoxModerateComments.isChecked() ? true
        : false);
    album.setModerateMedia(checkBoxModerateMedia.isChecked() ? true : false);
    return album;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.album_fragment, null);
    id = (TextView) fragmentView.findViewById(R.id.txtId);
    links = (TextView) fragmentView.findViewById(R.id.txtLinks);
    createdAt = (TextView) fragmentView.findViewById(R.id.txtCreatedAt);
    updatedAt = (TextView) fragmentView.findViewById(R.id.txtUpdatedAt);
    shortcut = (TextView) fragmentView.findViewById(R.id.txtShortcut);
    description = (TextView) fragmentView.findViewById(R.id.txtDescription);
    user = (TextView) fragmentView.findViewById(R.id.txtUser);
    counters = (TextView) fragmentView.findViewById(R.id.txtCounters);
    checkBoxModerateComments = (CheckBox) fragmentView
        .findViewById(R.id.checkBoxModerateComments);
    checkBoxModerateMedia = (CheckBox) fragmentView
        .findViewById(R.id.checkBoxModerateMedia);
    editTextName = (EditText) fragmentView.findViewById(R.id.editTextName);
    return fragmentView;
  }

  public void updateAlbum() {
    collectAlbumData();
    GCAlbums.update(getActivity(), album, new UpdateAlbumCallback())
        .executeAsync();
  }

  public void createAlbum() {
    collectAlbumData();
    GCAlbums.create(getActivity(), album, new CreateAlbumCallback())
        .executeAsync();
  }

  private final class UpdateAlbumCallback implements
      HttpCallback<ResponseModel<AlbumModel>> {

    @Override
    public void onSuccess(ResponseModel<AlbumModel> responseData) {
      Toast.makeText(getActivity(),
          getResources().getString(R.string.txt_album_updated),
          Toast.LENGTH_SHORT).show();
      setAlbumModel(responseData.getData());
    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      Toast.makeText(
          getActivity(),
          getResources().getString(R.string.txt_error_occurred) + " "
              + responseCode.getStatusMessage(),
          Toast.LENGTH_SHORT).show();
      Log.e(TAG, getResources().getString(R.string.txt_error_occurred)
          + " " + responseCode.getStatusMessage());

    }

  }

  private final class CreateAlbumCallback implements
      HttpCallback<ResponseModel<AlbumModel>> {

    @Override
    public void onSuccess(ResponseModel<AlbumModel> responseData) {
      Toast.makeText(getActivity(),
          getResources().getString(R.string.txt_album_created),
          Toast.LENGTH_SHORT).show();
      setAlbumModel(responseData.getData());
    }

    @Override
    public void onHttpError(ResponseStatus responseCode) {
      Toast.makeText(
          getActivity(),
          getResources().getString(R.string.txt_error_occurred) + " "
              + responseCode.getStatusMessage(),
          Toast.LENGTH_SHORT).show();
      Log.e(TAG, getResources().getString(R.string.txt_error_occurred)
          + " " + responseCode.getStatusMessage());
    }

  }
}
