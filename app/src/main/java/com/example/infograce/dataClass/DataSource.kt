package com.example.infograce.dataClass

import com.example.infograce.R

class DataSource {
    companion object{
        fun createDataSet():ArrayList<RecyclerViewItems> {
            var list = ArrayList<RecyclerViewItems>()
            list.add(
                RecyclerViewItems.LayersGroup(
                    "Последние слои",
                    "11.03.2022",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.grometry_collection,
                    RecyclerViewItems.TitleSpannable("Слой делян"),
                    "60%",
                    241,
                    "16-18",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.waypoint,
                    RecyclerViewItems.TitleSpannable("Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом"),
                    "60%",
                    241,
                    "16-18",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.line,
                    RecyclerViewItems.TitleSpannable("Преграды для прохождения огня"),
                    "60%",
                    241,
                    "16-18",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 02.07.2021"),
                    "60%",
                    241,
                    "16-18",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 02.07.2021"),
                    "60%",
                    241,
                    "16-18",
                    Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.LayersGroup(
                    "Общие слои",
                    "11.03.2022",
                    Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.folder,
                    RecyclerViewItems.TitleSpannable("Папка со слоями"),
                    "60%",
                    241,
                    "16-18",
                    Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon_hatched_2,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 01.07.2021"),
                    "60%",
                    241,
                    "16-18",
                    Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "60%",
                    241,
                    "16-18",
                    Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "60%",
                    241,
                    "16-18",
                    Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "60%",
                    241,
                    "16-18",
                    Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "60%",
                    241,
                    "16-18",
                    Group.RED
                )
            )
            return list
        }
    }

}