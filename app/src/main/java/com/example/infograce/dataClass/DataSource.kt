package com.example.infograce.dataClass

import com.example.infograce.R
import com.example.infograce.dataClass.models.Layer
import com.example.infograce.dataClass.models.Source

class DataSource {
    companion object {
        fun createDataSet(): ArrayList<RecyclerViewItems> {
            val list = ArrayList<RecyclerViewItems>()
            list.add(
                RecyclerViewItems.LayersGroup(
                    "Последние слои",
                    "11.03.2022",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("METEO"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "METEO",
                        sourceLayer = "METEO",
                        fillColor = 7766,
                        fillOpacity = 1.0,
                        fillOutlineColor = 56654,
                        source = Source(
                            sourceId = "1e6rib3l",
                            url = "mapbox://azmetov.1e6rib3l"
                        )
                    )
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    icon = R.drawable.polygon,
                    title = RecyclerViewItems.TitleSpannable("CTR"),
                    trans = "60%",
                    elem = 241,
                    zoom = "16-18",
                    group = Group.RED,
                    data = Layer(
                        layerId = "CTR",
                        sourceLayer = "CTR",
                        fillColor = 76576,
                        fillOpacity = 0.5,
                        fillOutlineColor = 23423,
                        source = Source(
                            sourceId = "1e6rib3l",
                            url = "mapbox://azmetov.1e6rib3l"
                        )
                    )
                )
            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.waypoint,
//                    RecyclerViewItems.TitleSpannable("Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.BLUE
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.line,
//                    RecyclerViewItems.TitleSpannable("Преграды для прохождения огня"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.BLUE
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 02.07.2021"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.BLUE
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 02.07.2021"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.BLUE
//                )
//            )
//            list.add(
//                RecyclerViewItems.LayersGroup(
//                    "Общие слои",
//                    "11.03.2022",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.folder,
//                    RecyclerViewItems.TitleSpannable("Папка со слоями"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon_hatched_2,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 01.07.2021"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
//            list.add(
//                RecyclerViewItems.Layers(
//                    R.drawable.polygon,
//                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
//                    "60%",
//                    241,
//                    "16-18",
//                    Group.RED
//                )
//            )
            return list
        }
    }

}