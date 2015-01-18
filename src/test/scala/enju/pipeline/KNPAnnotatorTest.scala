package enju.pipeline

import java.util.Properties
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class KNPAnnotatorTest extends FunSuite {
  test("getTokens") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-0.93093
                   |* -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><判定詞><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><用言代表表記:太郎/たろう><時制-無時制><照応詞候補:太郎><格解析結果:太郎/たろう:判0:ガ/U/-/-/-/-;ニ/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ノ/U/-/-/-/-;ガ２/U/-/-/-/-;ニトル/U/-/-/-/-><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><文末><表現文末><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞>
                   |EOS
""".stripMargin.split("\n").toSeq

    val expected_tokens = <tokens><token surf="太郎" reading="たろう" base="太郎" pos="名詞" pos_id="6" pos1="人名" pos1_id="5" inflectionType="*" inflectionType_id="0" inflectionForm="*" inflectionForm_id="0" features="人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう &lt;人名:日本:名:45:0.00106&gt;&lt;疑似代表表記&gt;&lt;代表表記:太郎/たろう&gt;&lt;正規化代表表記:太郎/たろう&gt;&lt;文頭&gt;&lt;文末&gt;&lt;表現文末&gt;&lt;漢字&gt;&lt;かな漢字&gt;&lt;名詞相当語&gt;&lt;自立&gt;&lt;内容語&gt;&lt;タグ単位始&gt;&lt;文節始&gt;&lt;固有キー&gt;&lt;文節主辞&gt;" id="s0_tok0"/></tokens>

    val knp = new KNPAnnotator("knp", new Properties)

    knp.getTokens(input, "s0") should be(expected_tokens)
  }

  test("getBasicPhrases") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-0.93093
                   |* -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><判定詞><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><用言代表表記:太郎/たろう><時制-無時制><照応詞候補:太郎><格解析結果:太郎/たろう:判0:ガ/U/-/-/-/-;ニ/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ノ/U/-/-/-/-;ガ２/U/-/-/-/-;ニトル/U/-/-/-/-><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><文末><表現文末><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞>
                   |EOS
""".stripMargin.split("\n").toSeq

    val feature = "<文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><判定詞><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><用言代表表記:太郎/たろう><時制-無時制><照応詞候補:太郎><格解析結果:太郎/たろう:判0:ガ/U/-/-/-/-;ニ/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ノ/U/-/-/-/-;ガ２/U/-/-/-/-;ニトル/U/-/-/-/-><EID:0>"
    val expected = <basic_phrases><basic_phrase id="s0_bp0" tokens="s0_tok0" features={feature} /></basic_phrases>

    val knp = new KNPAnnotator("knp", new Properties)

    knp.getBasicPhrases(input, "s0") should be(expected)
  }

  test("isChunk") {
    val input = "* -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isChunk(input) should be(true)
  }

  test("isBasicPhrase") {
    val input = "+ -1D <文頭><文末><人名><体言><用言:判><体言止><レベル:C><区切:5-5><ID:（文末）><裸名詞><提題受:30><主節><状態述語><判定詞><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><用言代表表記:太郎/たろう><時制-無時制><照応詞候補:太郎><格解析結果:太郎/たろう:判0:ガ/U/-/-/-/-;ニ/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ノ/U/-/-/-/-;ガ２/U/-/-/-/-;ニトル/U/-/-/-/-><EID:0>"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isBasicPhrase(input) should be(true)
  }

  test("isDocInfo") {
    val input = "# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-0.93093"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isDocInfo(input) should be(true)
  }

  test("isToken") {
    val input = "太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 \"人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう\" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><文末><表現文末><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞>"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isToken(input) should be(true)
  }

  test("isEOS") {
    val input = "EOS"

    val knp = new KNPAnnotator("knp", new Properties)
    knp.isEOS(input) should be(true)
  }

  test("getBasicPhrase 2") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq


    val feature1 = "<文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>"
    val feature2 = "<文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>"
    val expected = <basic_phrases><basic_phrase id="s0_bp0" tokens="s0_tok0 s0_tok1" features={feature1} /><basic_phrase id="s0_bp1" tokens="s0_tok2 s0_tok3" features={feature2} /></basic_phrases>

    val knp = new KNPAnnotator("knp", new Properties)

    knp.getBasicPhrases(input, "s0") should be(expected)
  }

  test("getChunks") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val feature1 = "<文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>"
    val feature2 = "<文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>"

    val expected = <chunks><chunk id="s0_chu0" tokens="s0_tok0 s0_tok1" features={feature1} /><chunk id="s0_chu1" tokens="s0_tok2 s0_tok3" features={feature2}/></chunks>

    val knp = new KNPAnnotator("knp", new Properties)
    knp.getChunks(input, "s0") should be(expected)
  }

  test("getBasicPhraseDependencies") {
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val expected = <basic_phrase_dependencies root="s0_bp1"><basic_phrase_dependency id="s0_bpdep0" head="s0_bp1" dependent="s0_bp0" label="D"/></basic_phrase_dependencies>


    val knp = new KNPAnnotator("knp", new Properties)
    knp.getBasicPhraseDependencies(input, "s0") should be(expected)
  }

  test("getDependencies"){
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val expected = <dependencies root="s0_chu1"><dependency id="s0_dep0" head="s0_chu1" dependent="s0_chu0" label="D"/></dependencies>

    val knp = new KNPAnnotator("knp", new Properties)
    knp.getDependencies(input, "s0") should be(expected)
  }

  test("getCaseRelations"){
    val input = """|# S-ID:1 KNP:4.11-CF1.1 DATE:2015/01/13 SCORE:-7.16850
                   |* 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:太郎/たろう><主辞代表表記:太郎/たろう>
                   |+ 1D <文頭><人名><ガ><助詞><体言><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><SM-人><SM-主体><正規化代表表記:太郎/たろう><NE:PERSON:太郎><照応詞候補:太郎><解析格:ガ><EID:0>
                   |太郎 たろう 太郎 名詞 6 人名 5 * 0 * 0 "人名:日本:名:45:0.00106 疑似代表表記 代表表記:太郎/たろう" <人名:日本:名:45:0.00106><疑似代表表記><代表表記:太郎/たろう><正規化代表表記:太郎/たろう><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><固有キー><文節主辞><係:ガ格><NE:PERSON:S>
                   |が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
                   |* -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
                   |+ -1D <文末><時制-過去><句点><用言:動><レベル:C><区切:5-5><ID:（文末）><係:文末><提題受:30><主節><格要素><連用要素><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><主題格:一人称優位><格関係0:ガ:太郎><格解析結果:走る/はしる:動13:ガ/C/太郎/0/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:1><述語項構造:走る/はしる:動13:ガ/C/太郎/0>
                   |走った はしった 走る 動詞 2 * 0 子音動詞ラ行 10 タ形 10 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><表現文末><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
                   |。 。 。 特殊 1 句点 1 * 0 * 0 NIL <文末><英記号><記号><付属>
                   |EOS
""".stripMargin.split("\n").toSeq

    val expected = <case_relations><case_relation id="s0_cr0" head="s0_bp1" depend="s0_tok0" label="ガ" flag="C" /><case_relation id="s0_cr1" head="s0_bp1" depend="unk" label="ヲ" flag="U" /><case_relation id="s0_cr2" head="s0_bp1" depend="unk" label="ニ" flag="U" /><case_relation id="s0_cr3" head="s0_bp1" depend="unk" label="ト" flag="U" /><case_relation id="s0_cr4" head="s0_bp1" depend="unk" label="デ" flag="U" /><case_relation id="s0_cr5" head="s0_bp1" depend="unk" label="カラ" flag="U" /><case_relation id="s0_cr6" head="s0_bp1" depend="unk" label="ヨリ" flag="U" /><case_relation id="s0_cr7" head="s0_bp1" depend="unk" label="マデ" flag="U" /><case_relation id="s0_cr8" head="s0_bp1" depend="unk" label="時間" flag="U" /><case_relation id="s0_cr9" head="s0_bp1" depend="unk" label="外の関係" flag="U" /><case_relation id="s0_cr10" head="s0_bp1" depend="unk" label="ノ" flag="U" /><case_relation id="s0_cr11" head="s0_bp1" depend="unk" label="修飾" flag="U" /><case_relation id="s0_cr12" head="s0_bp1" depend="unk" label="トスル" flag="U" /><case_relation id="s0_cr13" head="s0_bp1" depend="unk" label="ニオク" flag="U" /><case_relation id="s0_cr14" head="s0_bp1" depend="unk" label="ニカンスル" flag="U" /><case_relation id="s0_cr15" head="s0_bp1" depend="unk" label="ニヨル" flag="U" /><case_relation id="s0_cr16" head="s0_bp1" depend="unk" label="ヲフクメル" flag="U" /><case_relation id="s0_cr17" head="s0_bp1" depend="unk" label="ヲハジメル" flag="U" /><case_relation id="s0_cr18" head="s0_bp1" depend="unk" label="ヲノゾク" flag="U" /><case_relation id="s0_cr19" head="s0_bp1" depend="unk" label="ヲツウジル" flag="U" /></case_relations>

    val knp = new KNPAnnotator("knp", new Properties)
    val tokens = knp.getTokens(input, "s0")
    val bps = knp.getBasicPhrases(input, "s0")
    knp.getCaseRelations(input, tokens, bps, "s0") should be (expected)
  }

  test("getCaseRelations 2"){
val input = """|# S-ID:1 KNP:4.12-CF1.1 DATE:2015/01/18 SCORE:-22.40233
|* 1D <文頭><ガ><助詞><体言><一文字漢字><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:背/せ><主辞代表表記:背/せ>
|+ 1D <文頭><ガ><助詞><体言><一文字漢字><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><正規化代表表記:背/せ><照応詞候補:背><解析格:ガ><EID:0><述語項構造:背/せ:名1>
|背 せ 背 名詞 6 普通名詞 1 * 0 * 0 "代表表記:背/せ 漢字読み:訓 〜を〜に構成語 カテゴリ:動物-部位;場所-機能" <代表表記:背/せ><漢字読み:訓><〜を〜に構成語><カテゴリ:動物-部位;場所-機能><正規化代表表記:背/せ><文頭><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><文節主辞><係:ガ格>
|が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
|* 2D <連体修飾><用言:形><係:連格><レベル:B-><区切:0-5><ID:（形判連体）><連体節><状態述語><正規化代表表記:高い/たかい><主辞代表表記:高い/たかい>
|+ 2D <連体修飾><用言:形><係:連格><レベル:B-><区切:0-5><ID:（形判連体）><連体節><状態述語><正規化代表表記:高い/たかい><用言代表表記:高い/たかい><時制-現在><時制-無時制><格関係0:ガ:背><格関係2:ガ２:人><格解析結果:高い/たかい:形10:ガ/C/背/0/0/1;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;ヘ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;修飾/U/-/-/-/-;ガ２/N/人/2/0/1;ノ/U/-/-/-/-;ニクラベル/U/-/-/-/-;トスル/U/-/-/-/-;トイウ/U/-/-/-/-;ニアワセル/U/-/-/-/-;ニトル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ニカギル/U/-/-/-/-><EID:1><述語項構造:高い/たかい:形10:ガ２/N/人/2;ガ/C/背/0>
|高い たかい 高い 形容詞 3 * 0 イ形容詞アウオ段 18 基本形 2 "代表表記:高い/たかい 反義:形容詞:安い/やすい;形容詞:低い/ひくい" <代表表記:高い/たかい><反義:形容詞:安い/やすい;形容詞:低い/ひくい><正規化代表表記:高い/たかい><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞><係:連格>
|* 3D <SM-主体><SM-人><ガ><助詞><体言><一文字漢字><係:ガ格><区切:0-0><格要素><連用要素><正規化代表表記:人/じん?人/ひと><主辞代表表記:人/じん?人/ひと>
|+ 3D <SM-主体><SM-人><ガ><助詞><体言><一文字漢字><係:ガ格><区切:0-0><格要素><連用要素><名詞項候補><先行詞候補><正規化代表表記:人/じん?人/ひと><照応詞候補:人><解析連格:ガ２><解析格:ガ><EID:2><述語項構造:人/じん?人/ひと:名1>
|人 じん 人 名詞 6 普通名詞 1 * 0 * 0 "代表表記:人/じん 漢字読み:音 カテゴリ:人" <代表表記:人/じん><漢字読み:音><カテゴリ:人><正規化代表表記:人/じん?人/ひと><品曖><ALT-人-ひと-人-6-1-0-0-"代表表記:人/ひと 漢字読み:訓 カテゴリ:人"><品曖-普通名詞><原形曖昧><漢字><かな漢字><名詞相当語><自立><内容語><タグ単位始><文節始><文節主辞><名詞曖昧性解消><係:ガ格>
|が が が 助詞 9 格助詞 1 * 0 * 0 NIL <かな漢字><ひらがな><付属>
|* -1D <文末><用言:動><レベル:C><区切:5-5><ID:（文末）><提題受:30><主節><動態述語><正規化代表表記:走る/はしる><主辞代表表記:走る/はしる>
|+ -1D <文末><用言:動><レベル:C><区切:5-5><ID:（文末）><提題受:30><主節><動態述語><正規化代表表記:走る/はしる><用言代表表記:走る/はしる><時制-未来><主題格:一人称優位><格関係2:ガ:人><格解析結果:走る/はしる:動13:ガ/C/人/2/0/1;ヲ/U/-/-/-/-;ニ/U/-/-/-/-;ト/U/-/-/-/-;デ/U/-/-/-/-;カラ/U/-/-/-/-;ヨリ/U/-/-/-/-;マデ/U/-/-/-/-;時間/U/-/-/-/-;外の関係/U/-/-/-/-;ノ/U/-/-/-/-;修飾/U/-/-/-/-;トスル/U/-/-/-/-;ニオク/U/-/-/-/-;ニカンスル/U/-/-/-/-;ニヨル/U/-/-/-/-;ヲフクメル/U/-/-/-/-;ヲハジメル/U/-/-/-/-;ヲノゾク/U/-/-/-/-;ヲツウジル/U/-/-/-/-><EID:3><述語項構造:走る/はしる:動13:ガ/C/人/2>
|走って はしって 走る 動詞 2 * 0 子音動詞ラ行 10 タ系連用テ形 14 "代表表記:走る/はしる" <代表表記:走る/はしる><正規化代表表記:走る/はしる><かな漢字><活用語><自立><内容語><タグ単位始><文節始><文節主辞>
|いる いる いる 接尾辞 14 動詞性接尾辞 7 母音動詞 1 基本形 2 "代表表記:いる/いる" <代表表記:いる/いる><正規化代表表記:いる/いる><文末><表現文末><かな漢字><ひらがな><活用語><付属>
|EOS
""".stripMargin.split("\n").toSeq


val expected = <case_relations><case_relation flag="C" label="ガ" depend="s0_tok0" head="s0_bp1" id="s0_cr0"/><case_relation flag="U" label="ニ" depend="unk" head="s0_bp1" id="s0_cr1"/><case_relation flag="U" label="ト" depend="unk" head="s0_bp1" id="s0_cr2"/><case_relation flag="U" label="デ" depend="unk" head="s0_bp1" id="s0_cr3"/><case_relation flag="U" label="カラ" depend="unk" head="s0_bp1" id="s0_cr4"/><case_relation flag="U" label="ヨリ" depend="unk" head="s0_bp1" id="s0_cr5"/><case_relation flag="U" label="マデ" depend="unk" head="s0_bp1" id="s0_cr6"/><case_relation flag="U" label="ヘ" depend="unk" head="s0_bp1" id="s0_cr7"/><case_relation flag="U" label="時間" depend="unk" head="s0_bp1" id="s0_cr8"/><case_relation flag="U" label="外の関係" depend="unk" head="s0_bp1" id="s0_cr9"/><case_relation flag="U" label="修飾" depend="unk" head="s0_bp1" id="s0_cr10"/><case_relation flag="N" label="ガ２" depend="s0_tok3" head="s0_bp1" id="s0_cr11"/><case_relation flag="U" label="ノ" depend="unk" head="s0_bp1" id="s0_cr12"/><case_relation flag="U" label="ニクラベル" depend="unk" head="s0_bp1" id="s0_cr13"/><case_relation flag="U" label="トスル" depend="unk" head="s0_bp1" id="s0_cr14"/><case_relation flag="U" label="トイウ" depend="unk" head="s0_bp1" id="s0_cr15"/><case_relation flag="U" label="ニアワセル" depend="unk" head="s0_bp1" id="s0_cr16"/><case_relation flag="U" label="ニトル" depend="unk" head="s0_bp1" id="s0_cr17"/><case_relation flag="U" label="ヲハジメル" depend="unk" head="s0_bp1" id="s0_cr18"/><case_relation flag="U" label="ニカギル" depend="unk" head="s0_bp1" id="s0_cr19"/><case_relation flag="C" label="ガ" depend="s0_tok3" head="s0_bp3" id="s0_cr20"/><case_relation flag="U" label="ヲ" depend="unk" head="s0_bp3" id="s0_cr21"/><case_relation flag="U" label="ニ" depend="unk" head="s0_bp3" id="s0_cr22"/><case_relation flag="U" label="ト" depend="unk" head="s0_bp3" id="s0_cr23"/><case_relation flag="U" label="デ" depend="unk" head="s0_bp3" id="s0_cr24"/><case_relation flag="U" label="カラ" depend="unk" head="s0_bp3" id="s0_cr25"/><case_relation flag="U" label="ヨリ" depend="unk" head="s0_bp3" id="s0_cr26"/><case_relation flag="U" label="マデ" depend="unk" head="s0_bp3" id="s0_cr27"/><case_relation flag="U" label="時間" depend="unk" head="s0_bp3" id="s0_cr28"/><case_relation flag="U" label="外の関係" depend="unk" head="s0_bp3" id="s0_cr29"/><case_relation flag="U" label="ノ" depend="unk" head="s0_bp3" id="s0_cr30"/><case_relation flag="U" label="修飾" depend="unk" head="s0_bp3" id="s0_cr31"/><case_relation flag="U" label="トスル" depend="unk" head="s0_bp3" id="s0_cr32"/><case_relation flag="U" label="ニオク" depend="unk" head="s0_bp3" id="s0_cr33"/><case_relation flag="U" label="ニカンスル" depend="unk" head="s0_bp3" id="s0_cr34"/><case_relation flag="U" label="ニヨル" depend="unk" head="s0_bp3" id="s0_cr35"/><case_relation flag="U" label="ヲフクメル" depend="unk" head="s0_bp3" id="s0_cr36"/><case_relation flag="U" label="ヲハジメル" depend="unk" head="s0_bp3" id="s0_cr37"/><case_relation flag="U" label="ヲノゾク" depend="unk" head="s0_bp3" id="s0_cr38"/><case_relation flag="U" label="ヲツウジル" depend="unk" head="s0_bp3" id="s0_cr39"/></case_relations>

    val knp = new KNPAnnotator("knp", new Properties)
    val tokens = knp.getTokens(input, "s0")
    val bps = knp.getBasicPhrases(input, "s0")
    knp.getCaseRelations(input, tokens, bps, "s0") should be (expected)

  }
}



