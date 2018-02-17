<div class="table-responsive">
    <table>
    <tr>
        <td class="text-center"><a href="/quest/list"><h2>Home</h2></a></td>
        <td class="text-center"><a href="/pack/list"><h2>Packs</h2></a></td>
        <td class="text-center"><a href="/stats/list"><h2>Stats</h2></a></td>
    </tr>
    </table>
</div>
<script>
  function millisToString(millis) {
    function addToResult(result, count, postfix) {
      if ((count || result) && resultSize <2) {
        if (result) {
          result += " ";
        }
        result += count + postfix;
        resultSize++;
      }
      return result;
    }

    var negative = millis < 0,
        result = "",
        resultSize = 0;
    if (negative) {
        millis = -millis;
    }
    var seconds = Math.floor(millis / 1000);

    var years = Math.floor(seconds / 31536000);
    result = addToResult(result, years, "Y");
    seconds -= years * 31536000;

    var days = Math.floor(seconds / 86400);
    result = addToResult(result, days, "D");
    seconds -= days * 86400;

    var hours = Math.floor(seconds / 3600);
    result = addToResult(result, hours, "H");
    seconds -= hours * 3600;

    var minutes = Math.floor(seconds / 60);
    result = addToResult(result, minutes, "M");
    seconds -= minutes * 60;

    result = addToResult(result, seconds, "S");
    if (negative) {
      result = "-" + result;
    }
    return result;
  }
</script>
