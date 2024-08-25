package com.watsidev.testapiretrofit.Model

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)