package sopt_jungnami_android.jungnami.data

data class CommunityCommentData (
        //아이디
        val commentid: Int,
        //작성 시간
        val timeset: String,
        //내용
        val content: String,
        //유저닉네임
        val user_nick: String,
        //유저 이미지
        val user_img: String,
        //댓글 수
        val recommentCnt: Int,
        //좋아요 수
        val commentlikeCnt: Int,
        //좋아요 여부(01.1)
        var islike: Int,
        // 유저 아이디
        val user_id : String
)