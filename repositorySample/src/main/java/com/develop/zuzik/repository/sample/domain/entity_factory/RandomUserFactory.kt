package com.develop.zuzik.repository.sample.domain.entity_factory

import com.develop.zuzik.repository.sample.domain.entity.User
import java.util.*

/**
 * User: zuzik
 * Date: 1/8/17
 */
class RandomUserFactory {

    private val names = arrayOf(
            "Dreama Hemby",
            "Marcelino Grossi",
            "Genny Auclair",
            "Felipa Sisson",
            "Meryl Maxie",
            "Margarete Buckalew",
            "Napoleon Sankey",
            "Sixta Giron",
            "Renetta Ocallaghan",
            "Frankie Kepler",
            "Jodie Kinnaman",
            "Janessa Chauncey",
            "Violet Quevedo",
            "Mira Milford",
            "Lezlie Lentine",
            "Francine Chitty",
            "Franchesca Colwell",
            "Rosalva Woodie",
            "Walker Bruen",
            "Aldo Wambach")
    private val ages = (18..55)
    private val random = Random()

    fun create(): User =
            User(
                    id = null,
                    name = names[random.nextInt(names.size)],
                    age = ages.elementAt(random.nextInt(ages.count())))
}