package me.jakejmattson.bot.commands

import me.jakejmattson.bot.data.Configuration
import dev.kord.core.entity.channel.TextChannel
import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands
import me.jakejmattson.discordkt.api.extensions.toSnowflake
import kotlin.random.Random

class Suggestions(private val configuration: Configuration) {
    fun suggestions() = commands("Suggestions") {
        guildCommand("Suggest") {
            description = "Make a suggestion for the server. The suggestion will be sent to the staff of this server for review and if approved, it will be put up in a public voting channel."
            execute(EveryArg) {
                val suggestion = args.first

                //Get guild configurations from config file
                val guildConfiguration = discord
                    .getInjectionObjects(Configuration::class)
                    .guildConfigurations

                //Return error if guild has not been configured for usage
                if (guildConfiguration[guild.id.value] == null) {
                    respond("${guild.name} has not been set up for taking suggestions. If you are an administrator, please run `s!configure` to first set up the guild.")
                    return@execute
                }

                //Add suggestion to config file before sending message
                //randomLong(100000, 999999) get a random number for "salting" so that same author can make multiple suggestions with multiple IDs.
                val salt = randomLong(100000, 999999)
                configuration.addSuggestion(author.asMember(guild.id), salt, suggestion)

                //Send message in staff review channel
                val staffReviewChannel = guildConfiguration[guild.id.value]?.staffReviewChannel?.value?.let {
                    discord.kord.getChannelOf<TextChannel>(
                        it.toSnowflake())
                }
                staffReviewChannel?.createMessage("**${author.username + "#" + author.discriminator}** made the suggestion: \n```${suggestion}```")
            }
        }
    }

    private fun randomLong(start: Int, end: Int): Long {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        val randomInt = Random(System.nanoTime()).nextInt(end - start + 1) + start
        return randomInt.toLong()
    }
}
