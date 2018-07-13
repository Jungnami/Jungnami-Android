package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.PartyDistrictLegistlatorListData

data class GetPartyDistrictLegislatorListResponse (
        var data: List<PartyDistrictLegistlatorListData>,
        var message: String
)
