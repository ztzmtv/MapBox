package com.example.infograce.dataClass

import com.example.infograce.R

class DataSource {
    companion object{
        fun createDataSet(search: String):ArrayList<RecyclerViewItems> {
            var list = ArrayList<RecyclerViewItems>()
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.grometry_collection,
                    RecyclerViewItems.TitleSpannable("Слой делян"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.waypoint,
                    RecyclerViewItems.TitleSpannable("Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.line,
                    RecyclerViewItems.TitleSpannable("Преграды для прохождения огня"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 02.07.2021"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 02.07.2021"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.BLUE
                )
            )
            list.add(
                RecyclerViewItems.LayersGroup(
                    "Общие слои",
                    group = Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.folder,
                    RecyclerViewItems.TitleSpannable("Папка со слоями"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon_hatched_2,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 01.07.2021"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.RED
                )
            )
            list.add(
                RecyclerViewItems.Layers(
                    R.drawable.polygon,
                    RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                    "Прозрачность: 60%",
                    "Синхр.: 12.02.2022",
                    "Эл-ов: 23",
                    "Зум: 16-18",
                    group = Group.RED
                )
            )
            return list
        }
        var addLayer = listOf(
            RecyclerViewItems.Layers(
                R.drawable.polygon,
                RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
                "Прозрачность: 60%",
                "Синхр.: 12.02.2022",
                "Эл-ов: 23",
                "Зум: 16-18",
                group = Group.RED
            ))
    }

}