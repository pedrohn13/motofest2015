package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.utils.AjudaListItem;
import br.edu.ufcg.embedded.motofest.utils.HelpAdapter;

/**
 * Created by Pedro on 29/09/2015.
 */
public class HelpFragment extends Fragment {

    private List<String> listDataHeader;
    private HashMap<String, List<AjudaListItem>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.ajuda_list);
        prepareListData();
        listView.setAdapter(new HelpAdapter(getActivity(), listDataHeader, listDataChild));
        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        String programacao = getString(R.string.programacao);
        AjudaListItem programacaoListItem = new AjudaListItem(getString(R.string.programacao_descricao));
        List<AjudaListItem> list = new ArrayList<>();
        list.add(programacaoListItem);
        listDataHeader.add(programacao);
        listDataChild.put(programacao, list);


        String youtube = getString(R.string.youtube);
        AjudaListItem youtubeListItem = new AjudaListItem(getString(R.string.youtube_descricao));
        list = new ArrayList<>();
        list.add(youtubeListItem);
        listDataHeader.add(youtube);
        listDataChild.put(youtube, list);

        String mapa = getString(R.string.title_map);
        AjudaListItem mapaListItem = new AjudaListItem(getString(R.string.mapa_descricao));
        list = new ArrayList<>();
        list.add(mapaListItem);
        listDataHeader.add(mapa);
        listDataChild.put(mapa, list);

        String marcador = getString(R.string.mark);
        AjudaListItem marcadorListItem = new AjudaListItem(getString(R.string.marker_decricao));
        list = new ArrayList<>();
        list.add(marcadorListItem);
        listDataHeader.add(marcador);
        listDataChild.put(marcador, list);

        String camera = getString(R.string.photo);
        AjudaListItem cameralist = new AjudaListItem(getString(R.string.camera_descricao));
        list = new ArrayList<>();
        list.add(cameralist);
        listDataHeader.add(camera);
        listDataChild.put(camera, list);

        String mainCamera = getString(R.string.main_camera);
        AjudaListItem mainCameraList = new AjudaListItem(getString(R.string.main_camera_descricao));
        list = new ArrayList<>();
        list.add(mainCameraList);
        listDataHeader.add(mainCamera);
        listDataChild.put(mainCamera, list);

        String selfie = getString(R.string.selfie);
        AjudaListItem selfieList = new AjudaListItem(getString(R.string.selfie_descricao));
        list = new ArrayList<>();
        list.add(selfieList);
        listDataHeader.add(selfie);
        listDataChild.put(selfie, list);

        String flash = getString(R.string.flash);
        AjudaListItem flashloadList = new AjudaListItem(getString(R.string.flash_descricao));
        list = new ArrayList<>();
        list.add(flashloadList);
        listDataHeader.add(flash);
        listDataChild.put(flash, list);

        String download = getString(R.string.download);
        AjudaListItem downloadList = new AjudaListItem(getString(R.string.download_descricao));
        list = new ArrayList<>();
        list.add(downloadList);
        listDataHeader.add(download);
        listDataChild.put(download, list);

        String timeline = getString(R.string.timeline);
        AjudaListItem feedloadList = new AjudaListItem(getString(R.string.timeline_descricao));
        list = new ArrayList<>();
        list.add(feedloadList);
        listDataHeader.add(timeline);
        listDataChild.put(timeline, list);

        String parceiros = getString(R.string.parceiros);
        AjudaListItem parceirosloadList = new AjudaListItem(getString(R.string.parceiros_descricao));
        list = new ArrayList<>();
        list.add(parceirosloadList);
        listDataHeader.add(parceiros);
        listDataChild.put(parceiros, list);

        String opniao = getString(R.string.Faleconosco);
        AjudaListItem opniaoloadList = new AjudaListItem(getString(R.string.faleconosco_descricao));
        list = new ArrayList<>();
        list.add(opniaoloadList);
        listDataHeader.add(opniao);
        listDataChild.put(opniao, list);






    }
}
