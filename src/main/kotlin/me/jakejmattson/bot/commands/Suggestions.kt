package me.jakejmattson.bot.commands

import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands
import java.awt.Color

fun suggestions() = commands("Suggestions") {
    command("Suggest") {
        description = "Make a suggestion for the server. The suggestion will be sent to the staff of this server for review and if approved, it will be put up in a public voting channel."
        execute(EveryArg) {
            val output = args.first
            respond(output)
            if (message.getGuildOrNull() != null) {
                message.delete()
            }
        }
    }
}