package me.jakejmattson.bot.commands

import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands

@Suppress("unused")
fun general() = commands("General") {
    command("Say") {
        description = "Make the bot say something."
        execute(EveryArg) {
            val output = args.first
            if (message.getGuildOrNull() != null) {
                message.delete()
            }
            respond(output)
        }
    }
}