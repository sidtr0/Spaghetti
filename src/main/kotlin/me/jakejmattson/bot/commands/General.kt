package me.jakejmattson.bot.commands

import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands
import java.awt.Color

fun general() = commands("General") {
    command("Say") {
        description = "Make the bot say something."
        execute(EveryArg) {
            val output = args.first
            respond(output)
            message.delete()
        }
    }
}

private fun genRandomColor() = Color(genRandomRGB(), genRandomRGB(), genRandomRGB())
private fun genRandomRGB() = (0..255).random()