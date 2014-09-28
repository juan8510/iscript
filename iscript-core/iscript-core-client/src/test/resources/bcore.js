 
/*! bcore.min.js 2014-05-08 17:47:55 */
!function(a) {
    function b(a) {
        this.instance_id = this["static"].index, this.pool = [], this["static"].index++, b.instances[this.instance_id] = this, "function" == typeof a && (this.ready = a, this["static"].pageReady ? this.readyHook() : d(this))
    }
    b.prototype.instance_id = null, b.instances = [], b.prototype.callbacks = [], b.prototype["static"] = {index: 0,pageReady: !1,req_over: !1}, b.prototype.options = {}, b.prototype.readyFun = [], b.prototype.ready = function() {
    }, b.prototype.readyHook = function() {
        this.ready()
    }, b.prototype.constFun = function(a) {
        "function" == typeof a && a()
    }, b.prototype.isLoaded = !1;
    var c = function(a) {
        a.isLoaded || (a.isLoaded = !0, a["static"].pageReady = !0, a.readyHook())
    }, d = function(b) {
        var d = b;
        if ("complete" === document.readyState)
            return setTimeout(function() {
                c(d)
            }, 0);
        if (document.addEventListener)
            document.addEventListener("DOMContentLoaded", function() {
                document.removeEventListener("DOMContentLoaded", arguments.callee, !1), function() {
                    return document.body ? void setTimeout(function() {
                        c(d)
                    }, 0) : setTimeout(arguments.callee, 1)
                }()
            }, !1), a.addEventListener("load", function() {
                document.removeEventListener("load", arguments.callee, !1), setTimeout(function() {
                    c(d)
                }, 0)
            }, !1);
        else if (document.attachEvent) {
            document.attachEvent("onreadystatechange", function() {
                "complete" === document.readyState && (document.detachEvent("onreadystatechange", arguments.callee), setTimeout(function() {
                    c(d)
                }, 0))
            }), a.attachEvent("onload", function() {
                document.detachEvent("onload", arguments.callee), setTimeout(function() {
                    c(d)
                }, 0)
            });
            var e = !1;
            try {
                e = null == a.frameElement
            } catch (f) {
            }
            document.documentElement.doScroll && e && !function() {
                try {
                    document.documentElement.doScroll("left")
                } catch (a) {
                    return setTimeout(arguments.callee, 1)
                }
                setTimeout(function() {
                    c(d)
                }, 0)
            }()
        }
    };
    b.prototype.jsonp = function(b) {
        var c = a.location.href;
        0 == c.indexOf("https://") && (b = b.replace("http://", "https://")), b += -1 === b.indexOf("?") ? "?random=" + (new Date).getTime() : "&random=" + (new Date).getTime();
        var d = document.createElement("script");
        d.setAttribute("src", b), d.setAttribute("charset", "utf-8"), document.getElementsByTagName("head")[0].appendChild(d)
    }, b.prototype.send = function(a, c, d) {
        var d = d || this;
        return d["static"].req_over ? (d.options.gid || (d.options.gid = BFDSubCookie.getCookiePart("bfd_g") || ""), void setTimeout(function() {
            var e = d.callbacks.length;
            if ("undefined" == typeof c && (c = function() {
            }), !(!a instanceof b.Request)) {
                d.callbacks.push(function(a) {
                    c(a), d.callbacks[e] = null, c = null
                });
                var f = a.query(), g = function() {
                    var a = [];
                    for (var c in d.options)
                        "string" == typeof d.options[c] && a.push(c + "=" + b.Request.prototype.combineStr(d.options[c]));
                    return a.join("&")
                }(), h = [];
                f && h.push(f), g && h.push(g), h.push("callback=BCore.instances[" + d.instance_id + "].callbacks[" + e + "]"), d.jsonp(a.getUrl() + (-1 === a.getUrl().indexOf("?") ? "?" : "&") + h.join("&"))
            }
        }, 100)) : setTimeout(function() {
            b.prototype.send(a, c, d)
        }, 200)
    }, b.prototype.extend = function(a, c) {
        var d = function(a, c) {
            b.call(this, a, c)
        };
        "undefined" != typeof a && (d = a), d.prototype = this, d.prototype._super = b;
        for (var e in c)
            "prototype" != e && (d[e] = c[e]);
        return d
    }, b.Request = function(a) {
        "undefined" != typeof a && (this.getUrl = function() {
            return a
        })
    }, b.Request.prototype.getUrl = function() {
        return ""
    }, b.Request.prototype.query = function() {
        var a = new Array;
        for (var b in this)
            "function" != typeof this[b] && 0 !== b.indexOf("__") && a.push(new Array(b, "=", this.combineStr(this[b])).join(""));
        return a.join("&")
    }, b.Request.prototype.combineStr = function(a, b) {
        function c(a) {
            return a = a.replace(new RegExp("^\\s*", "g"), ""), a = a.replace(new RegExp("\\s*$", "g"), "")
        }
        if ("undefined" == typeof a)
            return "undefined";
        if (null == a)
            return "null";
        (void 0 === b || null === b) && (b = "|");
        var d = "";
        switch (Object.prototype.toString.apply(a)) {
            case "[object Boolean]":
                d = a ? "true" : "false";
                break;
            case "[object Number]":
                d = a;
                break;
            case "[object String]":
                a = c(a), d = encodeURIComponent(a);
                break;
            case "[object Array]":
                for (var e = new Array, f = 0; f < a.length; ++f)
                    "object" != typeof a[f] && e.push(this.combineStr(a[f]));
                d = e.join(b);
                break;
            case "[object Object]":
                var e = new Array;
                for (var g in a)
                    "function" != typeof a[g] && "object" != typeof a[g] && e.push(this.combineStr(g) + ":" + this.combineStr(a[g]));
                d = e.join(b)
        }
        return d
    }, a.BCore = a.$Core = a.BCore || b
}(window), function(a) {
    var b = function() {
    };
    b.prototype = {$: function() {
            for (var a = 0; a < arguments.length; a++) {
                var b = argument[a];
                return "string" == typeof arguments[0] && (b = document.getElementById(arguments[0])), 1 == arguments.length ? b : "not support"
            }
        },jsonp: function(b, c) {
            var d = a.location.href;
            0 == d.indexOf("https://") && (b = b.replace("http://", "https://")), c || (b += -1 === b.indexOf("?") ? "?random=" + (new Date).getTime() : "&random=" + (new Date).getTime());
            var e = document.createElement("script");
            e.setAttribute("src", b), e.setAttribute("charset", "utf-8");
            var f = document.getElementsByTagName("head")[0];
            f.insertBefore(e, f.lastChild)
        },getChildByClass: function(a, b) {
            for (var c = new Array, d = 0; d < a.childNodes.length; d++) {
                var e = a.childNodes.item(d);
                if (1 == e.nodeType && e.className == b && c.push(e), e.hasChildNodes()) {
                    var f = this.getChildByClass(e, b);
                    0 != f.length && (c = c.concat(f))
                }
            }
            return c
        },getCookie: function(a) {
            return this.cookie(a)
        },cookie: function(a) {
            var b = void 0;
            arguments.length >= 2 && (b = arguments[1]);
            var c = new Date;
            if (c.setDate(c.getDate() + 1), exp = c, arguments.length >= 3) {
                var c = new Date;
                c.setDate(c.getDate() + parseInt(arguments[2])), exp = c
            }
            if ("undefined" == typeof b) {
                if (a) {
                    var d = document.cookie.match(new RegExp("(^| )" + a + "=([^;]*)(;|$)"));
                    return null != d ? decodeURI(d[2]) : null
                }
                return document.cookie
            }
            document.cookie = null !== b ? a + "=" + b + ";expires=" + exp + ";path=/;" : a + "=;expires=-1;path=/;"
        },setCookie: function(a, b, c, d, e) {
            var f = new Date, g = "";
            d ? f.setTime(f.getTime() + Number(c)) : f.setDate(f.getDate() + Number(c)), e || (g = "domain=" + this.getTopDomain() + ";"), document.cookie = a + "=" + encodeURI(b) + ";expires=" + f.toUTCString() + ";path=/;" + g
        },getRootDomain: function(a) {
            a = a.replace(/\/$/gi, ""), a = a.replace(/^(http|ftp|https|ssh):\/\//gi, ""), a = a.replace(/(.com|.info|.net|.net.cn|.org|.me|.mobi|.hk|.us|.biz|.xxx|.ca|.mx|.tv|.ws|.com.ag|.net.ag|.org.ag|.ag|.am|.asia|.at|.be|.com.br|.net.br|.com.bz|.net.bz|.bz|.cc|.com.co|.net.co|.com.co|.co|.de|.com.es|.nom.es|.org.es|.es|.eu|.fm|.fr|.gs|.co.in|.firm.in|.gen.in|.ind.in|.net.in|.org.in|.in|.it|.jobs|.jp|.ms|.com.mx|.nl|.nu|.co.nz|.net.nz|.org.nz|.se|.tc|.tk|.com.tw|.idv.tw|.org.tw|.tw|.co.uk|.me.uk|.org.uk|.vg|.com.cn|.gov|.gov.cn|.cn|.ha.cn)$/gi, "%divide%$1");
            var b = a.split("%divide%")[1];
            "undefined" == typeof b && (b = ""), a = a.split("%divide%")[0];
            var c = a.split(".");
            return "." + c[c.length - 1] + b
        },getDomain: function() {
            var b = a.location.href;
            return b = b.replace(/^(http|ftp|https|ssh):\/\//gi, ""), b = b.split("/")[0], b = b.replace(/\:\d+$/gi, "")
        },getTopDomain: function() {
            var a = (location.hostname + "/").match(/[\w-]+\.(com|info|net|org|me|mobi|hk|us|biz|xxx|ca|mx|tv|ws|am|asia|at|be|bz|cc|co|de|nom|es|eu|fm|fr|gs|firm|gen|ind|in|it|jobs|jp|ms|nl|nu|se|tc|tk|idv|tw|vg|gov|cn|ha)(\.(cn|hk|jp|tw|kr|mo|uk|ag|es|co|nz|in|br|bz|mx))*\//gi);
            return a ? 0 < a.length ? a[0].substr(0, a[0].length - 1) : void 0 : document.domain
        },childElements: function(a) {
            var b = a.firstChild, c = [];
            for (b && 1 === b.nodeType && c.push(b); b; b = b.nextSibling)
                1 === b.nodeType && c.push(b);
            return c
        },siblings: function(a) {
            for (var b = a.parentNode.firstChild, c = []; b; b = b.nextSibling)
                1 === b.nodeType && b !== a && c.push(b);
            return c
        },prevAll: function(a) {
            for (var b = [], c = a.previousSibling; c && 9 !== c.nodeType && 1 !== c.nodeType; )
                b.push(c), c = c.previousSibling;
            return b
        },preSameTagAll: function(a) {
            for (var b = [], c = a.previousSibling; c; )
                1 == c.nodeType && c.nodeName == a.nodeName && b.push(c), c = c.previousSibling;
            return b
        },preSameTagsAll: function(a, b) {
            var c = [];
            cur = a;
            for (var d = 0; cur; )
                1 == cur.nodeType && cur.nodeName.toLowerCase() == b && (d++, c.push(cur)), cur = cur.nextSibling;
            return c
        },toNumber: function(a) {
            return a ? (a = a.toString(), a = a.replace(/<(S*?)[^>]*>.*?|<.*?\/>/gi, ""), a = a.replace(/[^\d\.]/gi, ""), parseFloat(a)) : 0
        },fillPath: function(a, b) {
            if (/^(https|http|ftp|sftp|ssh)/.test(a))
                return a;
            var c = location.host, d = location.href, d = d.replace(/^(http:\/\/|https:\/\/)/, ""), e = b || "http://";
            if (0 === a.indexOf("/")) {
                var f = e + c + a;
                return f
            }
            if (0 === a.indexOf("./")) {
                a = a.replace(/^.\//, "");
                var g = d.split("/"), h = g[0];
                g.pop(), g.shift();
                var f = e + h + "/" + g.join("/") + "/" + a;
                return f
            }
            if (0 === a.indexOf("../")) {
                for (; 0 === a.indexOf("../"); )
                    a = a.replace(/^..\//, "");
                var g = d.split("/"), h = g[0];
                g.pop(), g.shift();
                var f = e + h + "/" + g.join("/") + "/" + a;
                return f
            }
            var g = d.split("/"), h = g[0], f = e + h + "/" + a;
            return f
        },trim: function() {
            return arguments[0] && "string" == typeof arguments[0] ? arguments[0].replace(/(^\s*)|(\s*$)/g, "") : null
        },ltrim: function() {
            return arguments[0] && "string" == typeof arguments[0] ? arguments[0].replace(/(^\s*)/g, "") : null
        },rtrim: function() {
            return arguments[0] && "string" == typeof arguments[0] ? arguments[0].replace(/(\s*$)/g, "") : null
        },show: function(a, b) {
            return (a = $(a)) ? void (a.style.display = b || "") : !1
        },hide: function(a) {
            return (a = $(a)) ? void (a.style.display = "none") : !1
        },toggle: function(a) {
            return (a = $(a)) ? void (a.style.display = "none" != a.style.display ? "none" : value || "") : !1
        },bind: function(a, b, c) {
            return a.addEventListener ? (a.addEventListener(b, c, !1), !0) : a.attachEvent ? (a.attachEvent("on" + b, c), !0) : !1
        },rmbind: function(a, b, c) {
            a.removeEventListener ? a.removeEventListener(b, c, !1) : a.detachEvent ? a.detachEvent("on" + b, c) : a["on" + b] = null
        },removeRepeatArr: function(a) {
            for (var b = {}, c = [], d = 0; d < a.length; d++)
                b[a[d]] || ("" != a[d] && c.push(a[d]), b[a[d]] = 1);
            return c
        },mergeRepeat: function(a) {
            for (var b = [], c = a.length, d = 0; c > d; d++) {
                for (var e = d + 1; c > e; e++)
                    if (a[d][0] === a[e][0]) {
                        var f = parseInt(a[d][2]), g = parseFloat(a[d][1]), h = parseInt(a[e][2]), i = parseFloat(a[e][1]), j = ((f * g + h * i) / (f + h)).toFixed(2), k = f + h;
                        a[e] = [a[e][0], j, k], e = ++d
                    }
                b.push(a[d])
            }
            return b
        },getReqId: function(a) {
            var b = "req_id=", c = a.indexOf(b), d = a.slice(c + b.length), e = /[a-zA-Z0-9]+/;
            return d.match(e)
        },isFromRecommend: function(a) {
            return -1 == a.indexOf("req_id=") ? !1 : !0
        },getByteLen: function(a, b) {
            if (!a)
                return "";
            for (var c = "", d = 0, e = 0; e < a.length && (d += null != a.charAt(e).match(/[^\x00-\xff]/gi) ? 2 : 1, c += a.charAt(e), !(d > b)); e++)
                ;
            return c
        },getElByBFDPath: function(a) {
            if ("" == a || "string" != typeof a)
                return null;
            for (var c, d = a.split("/"), e = 1; e < d.length; e++)
                e + 1 < d.length && (1 == e && (c = document.getElementsByTagName("body")[0]), c = function(a, c) {
                    if (a && (narr = c.split("@"), "" != narr[4])) {
                        var d = b.prototype.preSameTagsAll(a.firstChild, narr[0]);
                        return d[narr[4]]
                    }
                }(c, d[e + 1]));
            return c
        },getSource: function(b) {
            for (var c, d = 0; d < b.length; d++) {
                var e = b[d].img;
                if (e) {
                    c = a.XMLHttpRequest ? new XMLHttpRequest : new ActiveXObject("Microsoft.XMLHTTP"), c.open("GET", e, !1), c.send(null);
                    var f = c.status;
                    404 == f && (b.splice(d, 1), d--), c = null
                } else
                    b.splice(d, 1), d--
            }
        },filterData: function(a, b) {
            if (a instanceof Array && b instanceof Array) {
                for (var c = 0; c < a.length; c++)
                    for (var d = c + 1; d < a.length; d++) {
                        for (var e = !1, f = 0; f < b.length; f++) {
                            var g = b[f];
                            if (void 0 === a[c][g] || a[c][g] !== a[d][g]) {
                                e = !1;
                                break
                            }
                            e = !0
                        }
                        e && (a.splice(d, 1), d--)
                    }
                this.getSource(a)
            }
        },getPath: function(a, b) {
            return b || (b = "@"), "HTML" == a.nodeName ? a.nodeName : this.getPath(a.parentNode, b) + b + a.nodeName
        },IsEmpty: function(a) {
            return void 0 == a || "-" == a || "" == a
        },Hash: function(a) {
            var b, c = 1, d = 0;
            if (!this.IsEmpty(a))
                for (c = 0, b = a.length - 1; b >= 0; b--)
                    d = a.charCodeAt(b), c = (c << 6 & 268435455) + d + (d << 14), d = 266338304 & c, c = 0 != d ? c ^ d >> 21 : c;
            return c
        },loadScript: function(a, b, c) {
            if (null == document.getElementById(b)) {
                var d = this, e = document.createElement("script");
                e.setAttribute("id", b), e.setAttribute("src", a), e.setAttribute("charset", "utf-8"), e.readyState ? d.bind(e, "readystatechange", function() {
                    ("loaded" === e.readyState || "complete" === e.readyState) && (c && c(), d.rmbind(e, "readystatechange", arguments.callee))
                }) : d.bind(e, "load", function() {
                    c && c(), d.rmbind(e, "load", arguments.callee)
                });
                var f = document.getElementsByTagName("head")[0];
                f.insertBefore(e, f.lastChild)
            }
        }};
    var c = function() {
        this.obj = {}, this.obj.o = {}, this.obj.a = {}
    };
    c.prototype.dedup = function(a) {
        var b = [];
        if (void 0 == a[0].iid)
            for (var c = 0; c < a.length; c++)
                this.obj.a[a[c]] || (b.push(a[c]), this.obj.a[a[c]] = !0);
        else
            for (var c = 0; c < a.length; c++)
                this.obj.o[a[c].iid] || (b.push(a[c]), this.obj.o[a[c].iid] = !0);
        return b
    };
    var d = {hname: function() {
            return b.prototype.getTopDomain() ? b.prototype.getTopDomain() : "localStatus"
        },isLocalStorage: function() {
            return a.localStorage ? !0 : !1
        },dataDom: null,initDom: function() {
            if (!this.dataDom)
                try {
                    this.dataDom = document.createElement("input"), this.dataDom.type = "hidden", this.dataDom.style.display = "none", this.dataDom.addBehavior("#default#userData");
                    var a = document.body;
                    a.insertBefore(this.dataDom, a.firstChild);
                    var b = new Date;
                    b.setDate(b.getDate() + 365), this.dataDom.expires = b.toUTCString()
                } catch (c) {
                    return !1
                }
            return !0
        },set: function(b, c) {
            try {
                this.isLocalStorage() ? a.localStorage.setItem(b, c) : this.initDom() && (this.dataDom.load(this.hname()), this.dataDom.setAttribute(b, c), this.dataDom.save(this.hname()))
            } catch (d) {
            }
        },get: function(b) {
            try {
                if (this.isLocalStorage())
                    return a.localStorage.getItem(b);
                if (this.initDom())
                    return this.dataDom.load(this.hname()), this.dataDom.getAttribute(b)
            } catch (c) {
            }
        },remove: function(a) {
            try {
                this.isLocalStorage() ? localStorage.removeItem(a) : this.initDom() && (this.dataDom.load(this.hname()), this.dataDom.removeAttribute(a), this.dataDom.save(this.hname()))
            } catch (b) {
            }
        }};
    BCore.tools = {}, BCore.tools.Tools = new b, BCore.tools.Repeat = c, BCore.tools.Tools.localData = d
}(window), function(a) {
    var b = {getDomain: function() {
            return BCore.tools.Tools.getTopDomain()
        },subCookieParts: {},setCookiePart: function(a, b) {
            if (b)
                try {
                    this.subCookieParts = this.getAllSubCookies(), a = a.toString(), b = b.toString(), this.subCookieParts[encodeURIComponent(a)] = encodeURIComponent(b), this.setSubCookieValue()
                } catch (c) {
                }
        },getCookiePart: function(a) {
            try {
                var b = "bfd_session_id=", c = document.cookie.indexOf(b), d = null, e = "";
                if (!(c > -1))
                    return null;
                if (d = document.cookie.indexOf(";", c), -1 == d && (d = document.cookie.length), d = document.cookie.substring(c + b.length, d), 0 < d.length) {
                    for (b = d.split("&"), c = 0, d = b.length; d > c; c++) {
                        var f = b[c].split("=");
                        if (decodeURIComponent(f[0]) == a) {
                            e = decodeURIComponent(f[1]);
                            break
                        }
                    }
                    return e
                }
            } catch (g) {
                return null
            }
        },getAllSubCookies: function() {
            var a = "bfd_session_id=", b = document.cookie.indexOf(a), c = null, d = {};
            if (b > -1) {
                if (c = document.cookie.indexOf(";", b), -1 == c && (c = document.cookie.length), c = document.cookie.substring(b + a.length, c), 0 < c.length)
                    for (a = c.split("&"), b = 0, c = a.length; c > b; b++) {
                        var e = a[b].split("=");
                        d[decodeURIComponent(e[0])] = decodeURIComponent(e[1])
                    }
                return d
            }
            return {}
        },setSubCookieValue: function() {
            var a = "bfd_session_id=", b = [];
            this.bfddate = new Date, this.now = parseInt(this.bfddate.getTime()), this.bfddate.setTime(this.now + 36e5);
            for (var c in this.subCookieParts)
                c && "function" != typeof this.subCookieParts[c] && b.push(c + "=" + this.subCookieParts[c]);
            0 < b.length ? (a += b.join("&"), a += "; expires=" + this.bfddate.toUTCString(), a += "; path=/; domain=" + this.getDomain() + ";") : a += "; expires=" + new Date(0).toUTCString(), document.cookie = a, this.subCookieParts = {}
        }};
    a.BFDSubCookie = b
}(window), function(a) {
    function b(a) {
        try {
            return document.getElementById(a).contentDocument || document.frames[a].document
        } catch (b) {
            return void 0
        }
    }
    var c = BCore.tools.Tools, d = {stop_frame: !1,stop_all: !1};
    if ("undefined" != typeof BCORE_CHECK_CONFIG && BCORE_CHECK_CONFIG.CHECK)
        for (var e in BCORE_CHECK_CONFIG.CHECK)
            d[e] = BCORE_CHECK_CONFIG.CHECK[e];
    var f = BCore.prototype;
    if (f["static"].req_over = !1, BFDSubCookie.getCookiePart("bfd_g"))
        f["static"].req_over = !0;
    else {
        var g = "";
        try {
            (-[1] || window.XMLHttpRequest) && (g = c.localData.get("_BGID_VAL") || "")
        } catch (h) {
        }
        c.IsEmpty(g) ? setTimeout(function() {
            var a = window.location.href, b = "http://ds.api.baifendian.com/2.0/StdID.do?bfdid=1";
            0 == a.indexOf("https://") && (b = b.replace("http://", "https://")), c.loadScript(b, "bfd_cache_id", function() {
                var a = BCore.prototype.options.gid;
                BFDSubCookie && BFDSubCookie.setCookiePart("bfd_g", a), f["static"].req_over = !0
            })
        }, 1) : (BFDSubCookie.setCookiePart("bfd_g", g), f["static"].req_over = !0)
    }
    !f["static"].checkload && f["static"].req_over && (setTimeout(function() {
        k(j)
    }, 1), f["static"].checkload = !0), f["static"].req_over || setTimeout(function() {
        f["static"].req_over = !0
    }, 5e3);
    var i = !1, j = function() {
        if (d.stop_all)
            return void function(a) {
                setTimeout(function() {
                    var b = a.BFDSubCookie ? a.BFDSubCookie.getCookiePart("bfd_g") : "";
                    if (null != b && b.toString().length > 5)
                        return setTimeout(arguments.callee, 1e3);
                    try {
                        return b = a.BCore.prototype.options.gid, a.BFDSubCookie && a.BFDSubCookie.setCookiePart("bfd_g", b), setTimeout(arguments.callee, 1e3)
                    } catch (c) {
                    }
                }, 1e3)
            }(window);
        var c = "https:" == a.location.protocol ? "https://" : "http://", e = c.indexOf("https") > -1 ? c + "ssl-" : c, f = e + "static.baifendian.com/api/check/index.js";
        if (window.ActiveXObject) {
            var g = document.createElement("script");
            return g.src = f, void document.getElementsByTagName("head")[0].appendChild(g)
        }
        var h = a.getElementById("add_speed_bfd");
        if (!h || null == h) {
            try {
                h = a.createElement('<iframe name="add_speed_bfd">')
            } catch (j) {
                h = a.createElement("iframe")
            }
            if (h.setAttribute("allowTransparency", "true"), h.setAttribute("id", "add_speed_bfd"), h.setAttribute("frameBorder", "0"), h.setAttribute("scrolling", "no"), h.style.cssText = "height:0px;width:0px;float:none;position:absolute;overflow:hidden;z-index:333333;margin:0;padding:0;border:0 none;background:none;", /msie/i.test(navigator.userAgent))
                try {
                    h.contentWindow.document
                } catch (k) {
                    h.src = "javascript:void((function(){document.open();document.domain='" + document.domain + "';document.write('baifendian');document.close()})())"
                }
            a.body.appendChild(h)
        }
        if ("object" == typeof h.contentWindow.document && window.ActiveXObject && (i = !0, h.doc = h.contentWindow.document), "complete" != h.readyState && "undefined" != typeof h.readyState)
            return setTimeout(arguments.callee, 1);
        var l = window.ActiveXObject ? 100 : 1;
        setTimeout(function() {
            try {
                if (h.doc || (h.doc = b("add_speed_bfd")), h.doc || (h.doc = h.contentDocument), h.doc || (h.doc = h.contentWindow.document), !h.doc)
                    throw new Error("no d.doc");
                var a = e + "static.baifendian.com/api/check/index.js", f = c + "ds.api.baifendian.com/2.0/speed.html", g = navigator.userAgent.indexOf("Firefox") > -1 ? !0 : !1;
                if (g)
                    h.onload = function() {
                        try {
                            var b = h.doc, c = document.createElement("script");
                            if (c.src = a, document.getElementsByTagName("head")[0].appendChild(c), !d.stop_frame) {
                                var e = b.createElement("iframe");
                                e.src = f, b.body.appendChild(e)
                            }
                        } catch (g) {
                        }
                    };
                else if (i && !g) {
                    var j = h.doc, k = document.createElement("script");
                    k.src = a;
                    var l = document.body;
                    if (l.insertBefore(k, l.firstChild), !d.stop_frame) {
                        var m = j.createElement("iframe");
                        m.src = f, j.body.appendChild(m)
                    }
                } else {
                    var n = "<body onload=\"var d = document;d.getElementsByTagName('head')[0].appendChild(d.createElement('script')).src='" + a + "';";
                    d.stop_frame || (n += "var f=d.createElement('iframe');f.src='" + f + "';d.body.appendChild(f);"), n += '">', h.doc.open().write(n), h.doc.close()
                }
            } catch (o) {
                var k = document.createElement("script");
                k.src = a, document.getElementsByTagName("head")[0].appendChild(k)
            }
        }, l)
    }, k = function(b) {
        var c = function() {
            if (!window.ActiveXObject && a && a.getElementsByTagName && a.getElementById && a.body)
                b();
            else
                try {
                    document.documentElement.doScroll("left"), b()
                } catch (d) {
                    return void setTimeout(c, 10)
                }
        };
        c()
    }
}(document), function() {
    function a(a) {
        G.call(this, this.__surl + a + ".do")
    }
    function b(b) {
        a.call(this, b)
    }
    function c(b, c) {
        a.call(this, b), null !== c && void 0 !== c && (this.iid = c)
    }
    function d() {
    }
    function e(a) {
        b.call(this, "AddItem"), this.iid = a
    }
    function f(a) {
        b.call(this, "RmItem"), this.iid = a
    }
    function g(a) {
        b.call(this, "AddCat"), this.cat = a
    }
    function h(a) {
        b.call(this, "RmCat"), this.cat = a
    }
    function i(a) {
        b.call(this, "AddUser"), void 0 !== a && (this.uid = a)
    }
    function j(a) {
        b.call(this, "RmUser"), void 0 !== a && (this.uid = a)
    }
    function k(a, c, d) {
        b.call(this, "Commit"), this.ord = a, this.ci = c, this.ici = d
    }
    function l(a) {
        c.call(this, "Visit", a)
    }
    function m(a) {
        c.call(this, "Review", a)
    }
    function n(a) {
        c.call(this, "VisitCat", null), this.cat = a
    }
    function o() {
        c.call(this, "AddCart", null)
    }
    function p(a) {
        c.call(this, "RmCart", a)
    }
    function q(a) {
        c.call(this, "AddFav", a)
    }
    function r(a) {
        c.call(this, "RmFav", a)
    }
    function s(a, b) {
        "undefined" == typeof b && (b = "rec"), c.call(this, "FeedBack", null), this.rid = a, this.app = b
    }
    function t(a) {
        c.call(this, "Search", null), this.qstr = a
    }
    function u(a) {
        c.call(this, "Order", null), this.ord = a
    }
    function v(a) {
        c.call(this, "Pay", null), this.ord = a
    }
    function w(a, b) {
        c.call(this, "VisitTag", null), this.tid = a, this.tval = b
    }
    function x(a) {
        c.call(this, "MouseClick", null), this.pth = a
    }
    function y() {
        c.call(this, "Register", null)
    }
    function z() {
        c.call(this, "Login", null)
    }
    function A(a) {
        c.call(this, "Scroll", null), this.per = a
    }
    function B(a) {
        c.call(this, "FootPrint", null), this.pt = a
    }
    function C() {
        c.call(this, "BannerPD", null)
    }
    function D() {
        c.call(this, "StayTime", null)
    }
    function E(a) {
        b.call(this, "AddNews"), this.iid = a
    }
    function F(a) {
        b.call(this, "RmNews"), this.iid = a
    }
    {
        var G = BCore.Request;
        BCore.tools.Tools
    }
    BCore.inputs = {}, a.prototype = new G, a.prototype.__surl = "http://ds.api.baifendian.com/2.0/", b.prototype = new a, c.prototype = new a, d.prototype.toString = function(a) {
        "undefined" == typeof a && (a = this);
        var b = "";
        switch (Object.prototype.toString.apply(a)) {
            case "[object Boolean]":
                b = a ? "true" : "false";
                break;
            case "[object Number]":
                b = a.toString();
                break;
            case "[object String]":
                b = 0 === a.indexOf("$") ? a : '"' + a + '"';
                break;
            case "[object Array]":
                for (var c = new Array, d = 0; d < a.length; ++d)
                    c.push(this.toString(a[d]));
                b = "[" + c.join(",") + "]";
                break;
            case "[object Object]":
                var c = new Array;
                for (var e in a)
                    "function" != typeof a[e] && c.push('"' + e + '":' + this.toString(a[e]));
                b = "{" + c.join(",") + "}"
        }
        return b
    }, e.prototype = new b, f.prototype = new b, g.prototype = new b, h.prototype = new b, i.prototype = new b, j.prototype = new b, k.prototype = new b, l.prototype = new c, m.prototype = new c, n.prototype = new c, o.prototype = new c, o.prototype.push = function(a) {
        "undefined" == typeof this.__pool_arr && (this.__pool_arr = []), this.__pool_arr.push(a), this.lst = (new d).toString(this.__pool_arr)
    }, p.prototype = new c, q.prototype = new c, r.prototype = new c, s.prototype = new c, t.prototype = new c, u.prototype = new c, u.prototype.push = function(a) {
        "undefined" == typeof this.__pool_arr && (this.__pool_arr = []), this.__pool_arr.push(a), this.lst = (new d).toString(this.__pool_arr)
    }, v.prototype = new c, v.prototype.push = function(a) {
        "undefined" == typeof this.__pool_arr && (this.__pool_arr = []), this.__pool_arr.push(a), this.lst = (new d).toString(this.__pool_arr)
    }, w.prototype = new c, x.prototype = new c, y.prototype = new c, z.prototype = new c, A.prototype = new c, B.prototype = new c, C.prototype = new c, D.prototype = new c, E.prototype = new b, F.prototype = new b, BCore.inputs.Input = a, BCore.inputs.UserAction = c, BCore.inputs.Resources = b, BCore.inputs.JObject = d, BCore.inputs.AddItem = e, BCore.inputs.RmItem = f, BCore.inputs.AddCat = g, BCore.inputs.RmCat = h, BCore.inputs.AddUser = i, BCore.inputs.RmUser = j, BCore.inputs.Commit = k, BCore.inputs.Visit = l, BCore.inputs.Review = m, BCore.inputs.AddCart = o, BCore.inputs.RmCart = p, BCore.inputs.AddFav = q, BCore.inputs.RmFav = r, BCore.inputs.FeedBack = s, BCore.inputs.Search = t, BCore.inputs.Pay = v, BCore.inputs.Order = u, BCore.inputs.VisitCat = n, BCore.inputs.VisitTag = w, BCore.inputs.MouseClick = x, BCore.inputs.Login = z, BCore.inputs.Register = y, BCore.inputs.Scroll = A, BCore.inputs.FootPrint = B, BCore.inputs.BannerPD = C, BCore.inputs.StayTime = D, BCore.inputs.AddNews = E, BCore.inputs.RmNews = F
}(window), function() {
    function a(a) {
        c.call(this, BCore.prototype.options.surl + "GetUserVH.do"), this.num = a
    }
    function b(a) {
        c.call(this, BCore.prototype.options.surl + "GetUserBH.do"), this.num = a
    }
    var c = BCore.Request;
    a.prototype = new c, b.prototype = new c, BCore.netuser = {}, BCore.netuser.GetUserVH = a, BCore.netuser.GetUserBH = b
}(window), function() {
    function a(a, b, c) {
        this.name = a, this.operator = b.toUpperCase(), this.value = c
    }
    function b(a, b, c) {
        return "( " + a.toString() + " ) " + b.toLowerCase() + " ( " + c.toString() + " )"
    }
    function c(a, b) {
        C.call(this, this.__surl + a + ".do"), "number" != typeof b || (this.num = b)
    }
    function d(a, b) {
        c.call(this, "RecFBT", b), this.iid = a
    }
    function e(a, b) {
        c.call(this, "RecVUB", b), this.iid = a
    }
    function f(a, b) {
        c.call(this, "RecVAV", b), this.iid = a
    }
    function g(a, b) {
        c.call(this, "RecBAB", b), this.iid = a
    }
    function h(a, b) {
        c.call(this, "RecCAC", b), this.iid = a
    }
    function i(a, b) {
        c.call(this, "RecFAF", b), this.iid = a
    }
    function j(a) {
        c.call(this, "RecByVH", a)
    }
    function k(a) {
        c.call(this, "RecByBH", a)
    }
    function l(a) {
        c.call(this, "RecByAH", a)
    }
    function m(a) {
        c.call(this, "HotBuy", a)
    }
    function n(a) {
        c.call(this, "RecForUser", a)
    }
    function o(a) {
        c.call(this, "RecCrossSite", a)
    }
    function p(a, b) {
        c.call(this, "RecSimiI", b), this.typ = "SIMI", this.iid = a
    }
    function q(a, b) {
        c.call(this, "RecSimiI", b), this.iid = a
    }
    function r(a) {
        c.call(this, "HotVisit", a)
    }
    function s(a, b) {
        if (this.p_bid = a, b && "[object object]" == Object.prototype.toString.call(b).toLowerCase())
            for (var d in b)
                b[d] && (this[d] = b[d]);
        c.call(this, "rec_" + a)
    }
    function t(a) {
        if ("string" == typeof a)
            this.iid = a;
        else if ("[object Array]" === Object.prototype.toString.apply(a))
            2 == a.length ? (this.iid = a[0], this.salesnum = a[1]) : (this.iid = a[0], this.name = a[1], this.url = a[2], this.img = a[3], this.price = a[4], this.weight = a[5]);
        else
            for (var b in a)
                this[b] = a[b]
    }
    function u(a) {
        if ("[object Object]" === Object.prototype.toString.apply(a) && "number" == typeof a.code)
            this.code = a.code, this.msg = a.msg, this.reqId = a.reqId, this.recs = a.recs;
        else if (this.code = a[0], this.msg = a[1], a[2] && (this.reqId = a[2]), a[3])
            if ("[object Array]" === Object.prototype.toString.apply(a[3])) {
                this.recs = new Array;
                for (var b = 0; b < a[3].length; ++b)
                    this.recs.push(new t(a[3][b]))
            } else
                this.recs = a[3]
    }
    function v(a) {
        var b = 1, c = a.substr(40, a.length);
        switch (c) {
            case "VAV":
                b = .5;
                break;
            case "BAB":
                b = .8;
                break;
            case "FBT":
                b = .8;
                break;
            case "VUB":
                b = .8;
                break;
            case "HotVisit":
                b = .5;
                break;
            case "HotBuy":
                b = .6;
                break;
            case "SimiI":
                b = .8;
                break;
            case "ForUser":
                b = .7
        }
        return b
    }
    function w(a) {
        var b = a.substr(0, 56) + "MERGE";
        return b
    }
    function x(a, b) {
        a = new u(a), b = new u(b);
        var c = "", d = -1, e = "", f = new Array;
        if (0 === a.code) {
            d = 0, e = "OK", "" === c && (c = w(a.reqId));
            for (var g = 0; g < a.recs.length; g++)
                "undefined" == typeof a.recs[g].weight && (f[g].weight = 1), a.recs[g].weight *= v(a.reqId);
            f = f.concat(a.recs)
        }
        if (0 === b.code) {
            d = 0, e = "OK", "" === c && (c = w(b.reqId));
            for (var g = 0; g < b.recs.length; g++)
                "undefined" == typeof b.recs[g].weight && (f[g].weight = 1), b.recs[g].weight *= v(b.reqId);
            f = f.concat(b.recs)
        }
        for (var h, i = new Array, j = {}, g = 0; null != (h = f[g]); g++)
            j[h.iid] || (i.push(f[g]), j[h.iid] = !0);
        f = i, f.sort(function(a, b) {
            return b.weight - a.weight
        });
        var k = new Object;
        return 0 === d ? (k.code = d, k.msg = e, k.reqId = c, k.recs = f) : (k.code = d, k.msg = "mergeError:have not available parm!"), new u(k)
    }
    function y(a, b) {
        c.call(this, "MRecVAV", b), this.iid = a
    }
    function z(a) {
        c.call(this, "MHotVisit", a)
    }
    function A(a) {
        c.call(this, "MRecByContent", a)
    }
    function B(a) {
        c.call(this, "RecFavourite", a)
    }
    {
        var C = BCore.Request;
        BCore.tools.Tools
    }
    BCore.recommends = {}, BCore.responses = {}, a.prototype.toString = function() {
        function a(a, b) {
            return "function" == typeof d.value ? "" : ("undefined" != typeof b ? b + " " : "") + d.name + " " + a + " " + ("string" == typeof d.value[0] ? '["' + d.value.join('","') + '"]' : "[" + d.value.join(",") + "]")
        }
        function b(a, b) {
            return "function" == typeof d.value ? "" : ("undefined" != typeof b ? b + " " : "") + d.name + " " + a + " " + d.value
        }
        function c(a, b) {
            return "string" == typeof d.value ? ("undefined" != typeof b ? b + " " : "") + d.name + " " + a + " '" + d.value + "'" : "number" == typeof d.value ? ("undefined" != typeof b ? b + " " : "") + d.name + " " + a + " " + d.value : ""
        }
        var d = this;
        return "EQ" === this.operator || "==" === this.operator ? c("eq") : "NE" === this.operator || "!=" === this.operator ? c("eq", "not") : "GE" === this.operator || ">=" === this.operator ? c("ge") : "GT" === this.operator || ">" === this.operator ? c("gt") : "LE" === this.operator || "<=" === this.operator ? c("le") : "LT" === this.operator || "<" === this.operator ? c("lt") : "IN" === this.operator ? a("in") : "NI" === this.operator ? a("in", "not") : "LIKE" === this.operator ? b("like") : "NL" === this.operator ? b("like", "not") : void 0
    }, c.prototype = new C, c.prototype.__surl = "http://rec.api.baifendian.com/2.0/", d.prototype = new c, e.prototype = new c, f.prototype = new c, g.prototype = new c, h.prototype = new c, i.prototype = new c, j.prototype = new c, k.prototype = new c, l.prototype = new c, m.prototype = new c, n.prototype = new c, o.prototype = new c, p.prototype = new c, q.prototype = new c, r.prototype = new c, s.prototype = new c, y.prototype = new c, z.prototype = new c, A.prototype = new c, B.prototype = new c, BCore.recommends.Recommend = c, BCore.recommends.RecFBT = d, BCore.recommends.RecVUB = e, BCore.recommends.RecVAV = f, BCore.recommends.RecCAC = h, BCore.recommends.RecFAF = i, BCore.recommends.RecByVH = j, BCore.recommends.RecByBH = k, BCore.recommends.RecByAH = l, BCore.recommends.RecByCart = p, BCore.recommends.RecBAB = g, BCore.recommends.HotBuy = m, BCore.recommends.RecSimiI = q, BCore.recommends.RecForUser = n, BCore.recommends.RecCrossSite = o, BCore.recommends.HotVisit = r, BCore.recommends.RecCommon = s, BCore.recommends.Filter = a, BCore.recommends.connectFilter = b, BCore.recommends.MRecVAV = y, BCore.recommends.MHotVisit = z, BCore.recommends.MRecByContent = A, BCore.recommends.RecFavourite = B, BCore.responses.Response = u, BCore.responses.mergeResponse = x
}(window), function() {
    function a() {
        return b.Hash(b.getTopDomain()) + "." + parseInt(99999999 * Math.random()) + "." + c
    }
    var b = BCore.tools.Tools, c = (new Date).getTime();
    BFDSubCookie.getCookiePart("bfd_s") ? BFDSubCookie.setCookiePart("bfd_s", BFDSubCookie.getCookiePart("bfd_s")) : BFDSubCookie.setCookiePart("bfd_s", a(), 36e5, !0), $Core = BCore = (new BCore).extend(function(b, c) {
        (null == this.options.sid || void 0 == this.options.sid || "" == this.options.sid) && (this.options.sid = BFDSubCookie.getCookiePart("bfd_s")), (null == this.options.sid || void 0 == this.options.sid || "" == this.options.sid) && (this.options.sid = a()), this._super.call(this, b, c)
    }, BCore)
}(window), function(a) {
    var b = BCore.tools.Tools, c = function() {
    };
    c.prototype = {options: {init: !1,security: "1",hook: "bfd_hook"},begin: function(c, d) {
            if (1 != this.options.init) {
                this.options.init = !0, c && (this.options.security = c), d && (this.options.hook = d);
                var e = this;
                b.bind(document, "mousedown", function(c) {
                    try {
                        var d = c || a.event, f = d.target || d.srcElement;
                        switch (Number(e.options.security)) {
                            case 0:
                                if ("undefined" == typeof e.options.hook || "" == e.options.hook)
                                    return;
                                var g = f.getAttribute(e.options.hook);
                                if (void 0 == g || null == g)
                                    return;
                                break;
                            case 1:
                                if ("undefined" != typeof e.options.hook && "" != e.options.hook)
                                    var g = f.getAttribute(e.options.hook);
                                break;
                            default:
                                return
                        }
                        var h = document.body.scrollTop || document.documentElement.scrollTop, i = (document.documentElement.offsetWidth, document.documentElement.clientHeight, d.clientX), j = d.clientY + h, k = "|", l = b.getPath(f, k);
                        l.indexOf(k) > -1 && new BCore(function() {
                            var a = new BCore.inputs.UserAction("MouseClick");
                            a.pth = l, a.lt = i, a.tp = j, g && (a.hook = g), this.send(a)
                        })
                    } catch (c) {
                    }
                })
            }
        }};
    var d = function() {
    };
    d.prototype = {save: function(a) {
            if (a && !(!a instanceof BCore.Request)) {
                var c = a.getUrl();
                if (!d.last || d.last != c + a.query()) {
                    d.last = c + a.query(), setTimeout(function() {
                        "" == d.last
                    }, 3e3);
                    var e = BCore.prototype;
                    !function() {
                        for (var b in e.options)
                            "string" == typeof e.options[b] && (a[b] || (a[b] = e.options[b]))
                    }();
                    var f = c + (-1 === c.indexOf("?") ? "?" : "&") + a.query(), g = b.cookie("tmc") || "";
                    g && "null" != g || (f = f.replace("&tmc=&", "&tmc=" + d._tmc + "&"));
                    var h = b.cookie("tma") || "";
                    h && "null" != h || (f = f.replace("&tma=&", "&tma=" + d._tma + "&"));
                    var i = b.cookie("tmd") || "";
                    i && "null" != i || (f = f.replace("&tmd=&", "&tmd=" + d._tmd + "&"));
                    var j = decodeURI(b.cookie("request_temp_url")) || "";
                    j && "null" != j && (f += "#" + j), b.setCookie("request_temp_url", f, 1)
                }
            }
        },send: function(a) {
            d._tmc = b.cookie("tmc") || "", d._tma = b.cookie("tma") || "", d._tmd = b.cookie("tmd") || "";
            var c = decodeURI(b.cookie("request_temp_url")) || "";
            if (c && "null" != c) {
                b.setCookie("request_temp_url", "", -1);
                for (var e = c.split("#"), f = 0, g = e.length; g > f; f++)
                    if ("" != e[f] && -1 != e[f].indexOf("http")) {
                        var h = e[f];
                        (new BCore).jsonp(h)
                    }
            }
        }}, setTimeout(function() {
        d.prototype.send()
    }, 2e3);
    var e = function(a) {
        for (var b in a)
            this.options[b] = a[b]
    };
    e.prototype = {elems: [],options: {autotime: 500,posH: 50,posV: 30},isInDocument: function(a) {
            for (var b = document.body.parentNode; a; ) {
                if (a === b)
                    return !0;
                a = a.parentNode
            }
            return !1
        },clear: function() {
            var a, b, c = this.elems;
            for (b = c.length - 1; b >= 0; b--)
                a = c[b].dom, this.isInDocument(a) || c.splice(b--, 1)
        },push: function(a, b, c) {
            if (!a || !b)
                return !1;
            if ("object" == typeof a) {
                this.clear();
                var d = new Object;
                d.dom = a, d.bid = b, c && (d.percent = c), this.elems.push(d), 1 == this.elems.length && this.loader()
            }
        },getOffsetTop: function(a) {
            for (var b = a.offsetTop, c = a.offsetParent; c; )
                b += c.offsetTop, c = c.offsetParent;
            return b
        },getDocSize: function(a) {
            return {w: a.offsetWidth,h: a.offsetHeight}
        },loader: function() {
            var a = this.options, b = "getOffsetTop", c = "scrollTop", d = "getDocSize", f = document.documentElement.clientHeight, g = function(a, b) {
                0 >= a - b && (b = a);
                var d = document.body[c] || document.documentElement[c];
                return a - b >= d && d >= a - f
            }, h = function() {
                e.prototype.loader()
            };
            elems = this.elems;
            for (var i, j, k, l, m = "tmpH", n = elems.length - 1; n >= 0; n--)
                if (i = !1, l = elems[n].dom, null != l) {
                    if (k = l.getAttribute(m), null === k || 0 == k) {
                        var o = this[d](l);
                        if (o.h < 50)
                            continue;
                        k = parseInt(elems[n].percent ? o.h * elems[n].percent : o.w > o.h ? o.h * a.posH : o.h * a.posV), l.setAttribute(m, parseInt(k / 100))
                    }
                    if (j = this[b](l), null !== j && 0 != j && (i = g(j, -k))) {
                        var p = new BCore.inputs.UserAction("DFeedBack");
                        p.hook = "show", p.p_bid = elems[n].bid, BCore.prototype.send(p), l.removeAttribute(m), elems.splice(n--, 1)
                    }
                }
            elems.length ? setTimeout(h, a.autotime) : elems = null
        }}, BCore.exts = {}, BCore.exts.MouseClick = c, BCore.exts.RequestSave = d, BCore.exts.BannerShow = e
}(window), function(window) {
    function i_(b) {
        return a.getElementByTagName(b) || null
    }
    function f() {
        this._BrowserDetect = {init: function() {
                this.browser = this.searchString(this.dataBrowser) || "None", this.version = this.searchVersion(navigator.userAgent) || this.searchVersion(navigator.appVersion) || "an unknown version", this.OS = this.searchString(this.dataOS) || "None"
            },searchString: function(a) {
                for (var b = 0; b < a.length; b++) {
                    var c = a[b].string, d = a[b].prop;
                    if (this.versionSearchString = a[b].versionSearch || a[b].identity, c) {
                        if (-1 != c.toLowerCase().indexOf(a[b].subString.toLowerCase()))
                            return a[b].identity
                    } else if (d)
                        return a[b].identity
                }
            },searchVersion: function(a) {
                var b = a.indexOf(this.versionSearchString);
                if (-1 != b)
                    return parseFloat(a.substring(b + this.versionSearchString.length + 1))
            },dataBrowser: [{string: navigator.userAgent,subString: "Maxthon",identity: "MaxThon"}, {string: navigator.userAgent,subString: "360se",identity: "360SE"}, {string: navigator.userAgent,subString: "theworld",identity: "TheWorld"}, {string: navigator.userAgent,subString: "MetaSr",identity: "Sogou"}, {string: navigator.userAgent,subString: "TencentTraveler",identity: "QQTT "}, {string: navigator.userAgent,subString: "MQQBrowser",identity: "MQQBrowser"}, {string: navigator.userAgent,subString: "QQBrowser",identity: "QQBrowser"}, {string: navigator.userAgent,subString: "UCWEB",identity: "UC"}, {string: navigator.userAgent,subString: "UC AppleWebKit",identity: "UC"}, {string: navigator.userAgent,subString: "Chrome",identity: "Chrome"}, {string: navigator.userAgent,subString: "OmniWeb",versionSearch: "OmniWeb/",identity: "OmniWeb"}, {string: navigator.vendor ? navigator.vendor : "",subString: "Apple",identity: "Safari",versionSearch: "Version"}, {prop: window.opera ? window.opera : "",identity: "Opera",versionSearch: "Version"}, {string: navigator.vendor ? navigator.vendor : "",subString: "iCab",identity: "iCab"}, {string: navigator.vendor ? navigator.vendor : "",subString: "KDE",identity: "Konqueror"}, {string: navigator.userAgent,subString: "BlackBerry",identity: "BlackBerry",versionSearch: "Version"}, {string: navigator.userAgent,subString: "Firefox",identity: "Firefox"}, {string: navigator.vendor ? navigator.vendor : "",subString: "Camino",identity: "Camino"}, {string: navigator.userAgent,subString: "Netscape",identity: "Netscape"}, {string: navigator.userAgent,subString: "MSIE",identity: "IE",versionSearch: "MSIE"}, {string: navigator.userAgent,subString: "Gecko",identity: "Mozilla",versionSearch: "rv"}, {string: navigator.userAgent,subString: "Mozilla",identity: "Netscape",versionSearch: "Mozilla"}],dataOS: [{string: navigator.userAgent,subString: "Windows NT 5.0",identity: "Win2000"}, {string: navigator.userAgent,subString: "Windows NT 5.1",identity: "WinXP"}, {string: navigator.userAgent,subString: "Windows NT 5.2",identity: "Win2003"}, {string: navigator.userAgent,subString: "Windows NT 6.0",identity: "WinVista"}, {string: navigator.userAgent,subString: "Windows NT 6.1",identity: "Win7"}, {string: navigator.userAgent,subString: "Windows Phone",identity: "WinPhone"}, {string: navigator.platform,subString: "Mac",identity: "Mac"}, {string: navigator.userAgent,subString: "iPhone",identity: "iPod"}, {string: navigator.userAgent,subString: "iPod",identity: "iPod"}, {string: navigator.userAgent,subString: "iPad",identity: "iPad"}, {string: navigator.userAgent,subString: "Android 1.0",identity: "Android 1.0"}, {string: navigator.userAgent,subString: "Android 1.1",identity: "Android 1.1"}, {string: navigator.userAgent,subString: "Android 2.0",identity: "Android 2.0"}, {string: navigator.userAgent,subString: "Android 2.1",identity: "Android 2.1"}, {string: navigator.userAgent,subString: "Android 2.2",identity: "Android 2.2"}, {string: navigator.userAgent,subString: "Android 2.3",identity: "Android 2.3"}, {string: navigator.userAgent,subString: "Android 3.0",identity: "Android 3.0"}, {string: navigator.userAgent,subString: "Android 3.1",identity: "Android 3.1"}, {string: navigator.userAgent,subString: "Android 4.0",identity: "Android 4.0"}, {string: navigator.userAgent,subString: "Android 4.1",identity: "Android 4.1"}, {string: navigator.userAgent,subString: "Android",identity: "Android"}, {string: navigator.userAgent,subString: "Linux",identity: "Linux"}]}, this._BrowserDetect.init.call(this._BrowserDetect), this.tag = {lo: ""}
    }
    function PageView() {
        UserAction.call(this, "PageView")
    }
    var bf = {sengin: ["baidu.com", "baidu.com", "google.com", "google.cn", "google.com.hk", "sogou.com", "zhongsou.com", "search.yahoo.com", "one.cn.yahoo.com", "soso.com", "114search.118114.cn", "search.live.com", "youdao.com", "gougou.com", "bing.com", "so.360.cn", "so.com"],sword: ["word", "wd", "q", "q", "q", "query", "w", "p", "p", "w", "kw", "q", "q", "search", "q", "q", "q"],_n: (new Date).getTime(),_t: ["rs=", "ja=", "oc=", "ln=", "lk=", "ep=", "ct=", "bt=", "ot=", "fv="],_s: 30}, BAE_CONFIG = {stop: !1,subCookie: !1}, a = document, h = a.location, d = window, g = navigator, orderArray = orderArray || [], j = encodeURIComponent, Tools = BCore.tools.Tools;
    f.prototype = {_ln: "",getFl: function() {
            this.tag.fl = d.screen.width > d.screen.height ? d.screen.width + "x" + d.screen.height : d.screen.height + "x" + d.screen.width
        },getJa: function() {
            this.tag.ja = g.javaEnabled() ? "1" : "0"
        },getDs: function() {
            var a;
            a = g.systemLanguage ? g.systemLanguage : g.browserLanguage ? g.browserLanguage : g.language ? g.language : g.userLanguage ? g.userLanguage : "", this.tag.ds = a.toLowerCase()
        },getLn: function() {
            var b = a.referrer || this._ln;
            this.tag.ln = b ? j(b) : ""
        },splitDir: function(a, b) {
            return a.replace("//", "/").split(b)
        },samDom: function(a, b) {
            if ("" == a || null == a)
                return !1;
            for (var c = this.splitDir(a, "/")[1], d = b.length, e = 0; d > e; e++) {
                var f = b[e], g = c.indexOf(f);
                if (g > -1)
                    return this.tag.ref = c, !0
            }
            return !1
        },getLo: function(a) {
            if ("" == a || null == a)
                return void (this.tag.lo = "");
            if (a.indexOf("?") >= 0) {
                var b = this.splitDir(a, "?");
                if (b.length > 0) {
                    for (var c = 0; c < b.length; c++)
                        if (b[c].indexOf("&") >= 0) {
                            for (var d = b[c].split("&"), e = 0; e < d.length; e++)
                                for (var f = d[e].split("="), g = 0; g < bf.sword.length; g++)
                                    if (f[0].toLowerCase() == bf.sword[g] && a.indexOf(bf.sengin[g]) >= 0)
                                        return void (this.tag.lo = f[1])
                        } else if (b[c].indexOf("=") >= 0)
                            for (var f = b[c].split("="), g = 0; g < bf.sword.length; g++)
                                if (f[0].toLowerCase() == bf.sword[g] && a.indexOf(bf.sengin[g]) >= 0)
                                    return void (this.tag.lo = f[1])
                } else
                    this.tag.lo = ""
            } else
                this.tag.lo = ""
        },getEp: function() {
            this.tag.ep = j(a.URL)
        },getPc: function() {
            var a = document.characterSet ? document.characterSet : document.charset;
            a = a.toLowerCase(), this.tag.ct = j(a)
        },getEt: function() {
            var b = a.title;
            if (null == b) {
                var c = i_("title");
                b = null != c && c.length > 0 ? c[0] : ""
            }
            this.tag.et = j(b.substr(0, 100))
        },getAgent: function() {
            return g.userAgent.toLowerCase()
        },getCp: function() {
            this.tag.cs = document.charset, this.tag.cp = "IE" === this._BrowserDetect.browser ? this._BrowserDetect.browser + " " + this._BrowserDetect.version : this._BrowserDetect.browser
        },getCw: function() {
            this.tag.cw = this._BrowserDetect.OS
        },getFs: function() {
            var f = "-";
            if (g.plugins && g.plugins.length) {
                for (var ii = 0; ii < g.plugins.length; ii++)
                    if (-1 != g.plugins[ii].name.indexOf("Shockwave Flash")) {
                        f = g.plugins[ii].description.split("Shockwave Flash ")[1];
                        break
                    }
            } else if (window.ActiveXObject)
                for (var ii = 10; ii >= 2; ii--)
                    try {
                        var fl = eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash." + ii + "');");
                        if (fl) {
                            f = ii + ".0";
                            break
                        }
                    } catch (e) {
                    }
            this.tag.fs = f
        },getProtocol: function() {
            return "https:" == h.protocol ? "https://" : "http://"
        },getAcc: function() {
            this.tag.cf = BAE_CONFIG.subCookie ? document.domain : Tools.getTopDomain()
        },isRandom: function() {
            return Math.ceil(1e8 * Math.random())
        },getS_d: function(a) {
            var b = new Date(a), c = b.getMonth() + 1, d = b.getDate();
            return 10 > c && (c = "0" + c), 10 > d && (d = "0" + d), b.getFullYear() + c + d
        },setSessiontimeout: function(a) {
            bf._s = a
        },getSessiontimeout: function() {
            return bf._s
        },IsEmpty: function(a) {
            return void 0 == a || "-" == a || "" == a
        },isCookie: function() {
            var a = [], b = "tma", c = "tmc", d = "tmd", e = Tools.Hash(this.tag.cf) + "." + this.isRandom() + "." + bf._n, f = bf._n, g = this.getSessiontimeout();
            if (k = 60 * g * 1e3, null == Tools.getCookie(c) || "" == Tools.getCookie(c))
                Tools.setCookie(c, "1." + e + "." + f + "." + f, k, 1, BAE_CONFIG.subCookie);
            else {
                var h = Tools.getCookie(c), i = h.split(".");
                try {
                    i[0] = parseInt(i[0]) + 1 + "", __n = (new Date).getTime(), i[4] = i[5], i[5] = __n
                } catch (j) {
                    Tools.setCookie(c, "0." + e + "." + f + "." + f, k, 1, BAE_CONFIG.subCookie)
                }
                Tools.setCookie(c, i.join("."), k, 1, BAE_CONFIG.subCookie)
            }
            var k = 62208e6;
            if (null == Tools.getCookie(b) || "" == Tools.getCookie(b))
                Tools.setCookie(b, e + "." + bf._n + "." + bf._n + ".1", k, 1, BAE_CONFIG.subCookie);
            else {
                var h = Tools.getCookie(b), i = h.split(".");
                if (6 == i.length) {
                    __n = (new Date).getTime();
                    try {
                        this.getS_d(parseInt(i[4])) < this.getS_d(parseInt(__n)) && (i[5] = parseInt(i[5]) + 1, i[3] = i[4], i[4] = __n)
                    } catch (j) {
                        Tools.setCookie(b, e + "." + bf._n + "." + bf._n + ".1", k, 1, BAE_CONFIG.subCookie)
                    }
                    Tools.setCookie(b, i.join("."), k, 1, BAE_CONFIG.subCookie)
                } else
                    Tools.setCookie(b, e + "." + bf._n + "." + bf._n + ".1", k, 1, BAE_CONFIG.subCookie)
            }
            k = 15552e6;
            var l = "0." + e;
            ("" == Tools.getCookie(d) || null == Tools.getCookie(d)) && Tools.setCookie(d, "0." + e + ".", k, 1, BAE_CONFIG.subCookie);
            var m = decodeURIComponent(this.tag.ln), n = document.URL || "";
            if ("" != m && n.indexOf("?") >= 0 && m.indexOf(this.tag.cf) < 0 && n.indexOf(this.tag.cf) >= 0) {
                var o = n.substring(n.indexOf("?") + 1, n.length), h = Tools.getCookie(d), i = h.split(".");
                if (o.indexOf("&") >= 0) {
                    for (var p = o.split("&"), q = 0; q < p.length; q++)
                        if (p[q].indexOf("t__") >= 0) {
                            var r = "";
                            i[i.length - 1] != r && Tools.setCookie(d, l + "." + r, k, 1, BAE_CONFIG.subCookie)
                        }
                } else {
                    var r = "";
                    i[i.length - 1] != r && Tools.setCookie(d, l + "." + r, k, 1, BAE_CONFIG.subCookie)
                }
            }
            if (Tools.getCookie(d)) {
                var h = Tools.getCookie(d), i = h.split(".");
                try {
                    i[0] = parseInt(i[0]) + 1 + ""
                } catch (j) {
                }
                Tools.setCookie(d, i.join("."), k, 1, BAE_CONFIG.subCookie)
            }
            return a.push(b + "=" + Tools.getCookie(b)), a.push(c + "=" + Tools.getCookie(c)), a.push(d + "=" + Tools.getCookie(d)), a.join("&")
        },init: function() {
            this.initLn(), this.getFl(), this.getJa(), this.getDs(), this.getLn(), this.getLo(this.samDom(a.referrer, bf.sengin) ? a.referrer : ""), this.getEp(), this.getEt(), this.getCp(), this.getCw(), this.getFs(), this.getPc(), this.getAcc()
        },initBridge: function() {
            return this.init(), bf._t[0] + this.tag.fl + "&" + bf._t[1] + this.tag.ja + "&" + bf._t[2] + this.tag.ds + "&" + bf._t[3] + this.tag.ln + "&" + bf._t[4] + this.tag.lo + "&" + bf._t[5] + this.tag.ep + "&" + bf._t[6] + this.tag.ct + "&" + bf._t[7] + this.tag.cp + "&" + bf._t[8] + this.tag.cw + "&" + bf._t[9] + this.tag.fs + "&"
        },initLn: function() {
            var b = this;
            if (!b._initLn) {
                if (!a.referrer) {
                    if (!document.body)
                        return setTimeout(arguments.callee, 1);
                    if (f.prototype._ln = Tools.getCookie("bfd_referrer") || "", this._BrowserDetect && "Sogou" === this._BrowserDetect.browser) {
                        Tools.bind(document.body, "mousedown", function() {
                            Tools.setCookie("bfd_referrer", h, 3e4, 1)
                        })
                    }
                }
                Tools.getCookie("bfd_referrer") && Tools.setCookie("bfd_referrer", "", -1), f.prototype._initLn = !0
            }
        },_initLn: !1}, $Core = BCore = (new BCore).extend(function(a, b) {
        this._super.call(this, a, b)
    }, BCore);
    var _super = BCore.prototype;
    if ("undefined" != typeof BCORE_CHECK_CONFIG && BCORE_CHECK_CONFIG.BAE)
        for (var i in BCORE_CHECK_CONFIG.BAE)
            BAE_CONFIG[i] = BCORE_CHECK_CONFIG.BAE[i];
    if (!BAE_CONFIG.stop && !_super["static"].baeload) {
        var _f = new f;
        _f.init(), _f.isCookie(), _super["static"].baeload = !0
    }
    var Request = BCore.Request, UserAction = BCore.inputs.UserAction, oldquery = UserAction.prototype.query;
    UserAction.prototype.query = function() {
        var a = oldquery.call(this);
        return [a, BCore.inputs.getCookie()].join("&")
    };
    var Recommend = BCore.recommends.Recommend;
    Recommend.prototype.query = function() {
        var a = oldquery.call(this);
        return [a, BCore.inputs.getCookie()].join("&")
    }, PageView.prototype = new UserAction, PageView.prototype.query = function() {
        var a = UserAction.prototype.query.call(this);
        return [a, BCore.inputs.getParamter()].join("&")
    }, BCore.inputs.getCookie = function() {
        if (!BAE_CONFIG.stop) {
            if (null == Tools.getCookie("tma") || null == Tools.getCookie("tmc") || null == Tools.getCookie("tmd")) {
                var a = new f;
                a.init(), a.isCookie()
            }
            return "tma=" + Tools.getCookie("tma") + "&tmc=" + Tools.getCookie("tmc") + "&tmd=" + Tools.getCookie("tmd")
        }
        return ""
    }, BCore.inputs.getParamter = function() {
        var a = new f;
        return "undefined" != typeof BCore.inputs.t && a.setSessiontimeout(BCore.inputs.t), a.initBridge()
    }, BCore.inputs.getLk = function() {
        var b = new f;
        return b.getLo(b.samDom(a.referrer, bf.sengin) ? a.referrer : ""), decodeURI(b.tag.lo)
    }, BCore.inputs.setSessiontimeout = function(a) {
        BCore.inputs.t = a
    }, BCore.inputs.PageView = PageView
}(window), function() {
    var a = navigator.userAgent.toLowerCase();
    if (/macintosh|mac os x/i.test(a) && "ipad" == a.match(/ipad/i)) {
        var b = BCore.prototype.send;
        BCore.prototype.send = function(a, c, d) {
            var d = d || this;
            if (a.getUrl().indexOf("/FeedBack.do") > -1) {
                var e = new BCore.exts.RequestSave;
                e.save(a)
            } else
                b(a, c, d)
        }
    }
}(window);
