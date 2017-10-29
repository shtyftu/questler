<div class="container">
    <div class="row">
        <div class="col text-center"><a href="/quest/list"><h2>Home</h2></a></div>
        <div class="col text-center"><a href="/pack/list"><h2>Packs</h2></a></div>
        <div class="col text-center"><a href="/proto/list"><h2>Protos</h2></a></div>
    </div>
</div>
<script>
  function millisToString(millis) {
    var result = "";
    function addToResult(count) {
      if (count || result) {
        if (result) {
          result += ":";
        }
        result += count;
      }
    }

    var seconds = Math.floor(millis / 1000);

    var years = Math.floor(seconds / 31536000);
    addToResult(years);
    seconds -= years * 31536000;

    var days = Math.floor(seconds / 86400);
    addToResult(days);
    seconds -= days * 86400;

    var hours = Math.floor(seconds / 3600);
    addToResult(hours);
    seconds -= hours * 3600;

    var minutes = Math.floor(seconds / 60);
    addToResult(minutes);
    seconds -= minutes * 60;

    addToResult(seconds);
    return result;
  }
</script>
