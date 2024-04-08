
app.controller("autoComplete", function ($scope, $http, $window) {
  loadHistorySearch = [];
  $scope.loadHistorySearch = function () {
    $http
      .get("/api/historySearch")
      .then((resp) => {
        loadHistorySearch = resp.data;

        console.log(loadHistorySearch);
      })
      .catch(function (err) {
        console.error(err); // xử lý lỗi khi gọi API
      });
  }
  $scope.loadHistorySearch(); // gọi lịch sử tìm kiếm

  let Searcher = (() => {
    let escapeRegExp = /[\-#$\^*()+\[\]{}|\\,.?\s]/g;
    let escapeReg = reg => reg.replace(escapeRegExp, '\\$&');
    let groupLeft = '',
      groupRight = '';
    let groupReg = new RegExp(escapeReg(groupRight + groupLeft), 'g');
    let groupExtractReg = new RegExp('(' + escapeReg(groupLeft) + '[\\s\\S]+?' + escapeReg(groupRight) + ')', 'g');
    let findMax = (str, keyword) => {
      let max = 0;
      keyword = groupLeft + keyword + groupRight;
      str.replace(groupExtractReg, m => {
        if (keyword == m) {
          max = Number.MAX_SAFE_INTEGER;
        } else if (m.length > max) {
          max = m.length;
        }
      });
      return max;
    };
    let keyReg = key => {
      let src = ['(.*?)('];
      let ks = key.split('');
      if (ks.length) {
        while (ks.length) {
          src.push(escapeReg(ks.shift()), ')(.*?)(');
        }
        src.pop();
      }
      src.push(')(.*?)');
      src = src.join('');
      let reg = new RegExp(src, 'i');
      let replacer = [];
      let start = key.length;
      let begin = 1;
      while (start > 0) {
        start--;
        replacer.push('$', begin, groupLeft + '$', begin + 1, groupRight);
        begin += 2;
      }
      replacer.push('$', begin);

      info = {
        regexp: reg,
        replacement: replacer.join('')
      };
      return info;
    };

    return {
      search(loadHistorySearch, keyword) {
        let kr = keyReg(keyword);
        let result = [];
        for (let e of loadHistorySearch) {
          if (kr.regexp.test(e)) {
            result.push(e.replace(kr.regexp, kr.replacement)
              .replace(groupReg, ''));
          }
        }
        result = result.sort((a, b) => findMax(b, keyword) - findMax(a, keyword));
        console.log(`result::::`, result)
        //create div 
        result = result.map(el => {
          return `${el}`
        })
        return result;
      }
    };

  })();
  const input = document.getElementById('search');
  const dropdown = document.getElementById('custom-dropdown');

  input.addEventListener('input', (e) => {
    const rs = Searcher.search(loadHistorySearch, e.target.value);

    // Clear previous options in the custom dropdown
    dropdown.innerHTML = '';

    // Populate the custom dropdown with new options
    rs.forEach((el) => {
      var anchor = document.createElement('a');
      var div = document.createElement('div');
      div.innerHTML = el;
      anchor.addEventListener('click', () => {
        // Populate the input field with the selected option
        input.value = el;
        dropdown.style.display = 'none';
      });

      anchor.appendChild(div);

      dropdown.appendChild(anchor);
      anchor.style = "text-decoration: none; color: black";
      anchor.setAttribute('href', '/product/search?search=' + el);
    });

    // Show the custom dropdown
    dropdown.style.display = 'block';

  });

  window.onclick = function (event) {
    dropdown.style.display = 'none';

  };


});