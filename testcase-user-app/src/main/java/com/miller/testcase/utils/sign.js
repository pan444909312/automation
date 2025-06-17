import md5 from 'blueimp-md5';

/**
 * 获取随机数
 * @param min   最小
 * @param max   最大
 * @returns {number}
 */
const getRndInteger = (min, max) => {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

/**
 * 获取随机字符串
 * @param length    长度
 * @returns {string}
 */
const getRandomString = (length) => {
    const str = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    const result = [];

    for (let i = 0; i < length; ++i) {
        const num = getRndInteger(0, str.length - 1);

        result.push(str[num]);
    }

    return result.join('');
};
/**
 * 提前url中的pathname
 * @param url
 * @returns {string}
 */
const extractPathname = (url) => {
    const protocolIndex = url.indexOf('://');
    const startIndex = Math.max.apply(
        null,
        [
            0,
            url.indexOf(
                '/',
                protocolIndex !== -1 ? protocolIndex + 3 : protocolIndex,
            ),
        ].filter(num => num !== -1),
    );
    const endIndex = Math.min.apply(
        null,
        [url.length, url.lastIndexOf('?'), url.lastIndexOf('#')].filter(
            num => num !== -1,
        ),
    );

    return url.substring(startIndex, endIndex);
};
/**
 * 签名
 * @param nt    时间戳
 * @param nu    请求url
 * @param nm    请求方法
 * @param nh    请求header
 * @param nb    请求body
 * @returns {{nv: string, nt, nn: string, nd: string}}
 */
const encode = (nt, nu, nm, nh, nb) => {
    const nn = getRandomString(25);

    const no = extractPathname(nu);
    const nmu = nm.toUpperCase();

    //第一轮加密
    const one_rand_string = '1234567890qwjnb';
    const one_encoded_string = md5(`${one_rand_string}${nmu}${nh}${no}`);
    //第二轮加密
    const two_rand_string = '3jlaiow323kjdsj';
    const two_encoded_string = md5(
        `${two_rand_string}${one_encoded_string}${nt}${nn}${nb}`,
    );

    const nd = two_encoded_string.substring(2, 17);

    return {
        nv: '2',
        nt,
        nn,
        nd,
    };
};

/**
 * 判断是否接口请求
 * @param url
 * @returns {boolean}
 */
const getIsApiProxy = (url) => {
    if (url.indexOf('://') !== -1) {
        if (!/:\/\/(api|app)(-|\.).+\.hungrypanda\./.test(url)) {
            return false;
        }
    }
    if (url.indexOf('/statics/global_config') !== -1) {
        return false;
    }

    return true;
};

/**
 * 请求增加签名
 * @param options {{url,method,headers,data}}
 * @returns {Promise<{url, method: string, headers: {}, data: ({pm, ph: ({}|{}), pd: (*|{}|{}), nv: string, nt: *, nn: string, nd: string}|FormData)}|*>}
 */
export const nse = async (options) => {
    //非接口请求
    if (!getIsApiProxy(options.url)) {
        return options;
    }

    const config = {...options};

    const headers = config.headers || {};

    if (
        headers.common &&
        headers.common !== null &&
        typeof headers.common === 'object'
    ) {
        Object.assign(headers, headers.common);
    }
    ['common', 'get', 'post', 'put', 'delete', 'head', 'patch'].forEach(
        method => {
            delete headers[method];
        },
    );

    config.method = config.method.toUpperCase();
    config.headers = headers;
    config.data = config.data || {};

    let data;

    if (typeof FormData !== 'undefined' && config.data instanceof FormData) {
        const newData = {};

        data = new FormData();

        (config.data).forEach((value, key) => {
            if (value instanceof File) {
                data.append(key, value);
            } else {
                newData[key] = value;
            }
        });
        const result = encode(
            Date.now().toString(),
            config.url,
            config.method,
            JSON.stringify(config.headers),
            JSON.stringify(newData),
        );
        data.append('pt', 'json');
        data.append('pm', config.method);
        data.append('ph', JSON.stringify(config.headers));
        data.append('pd', JSON.stringify(newData));
        data.append('nv', result.nv);
        data.append('nt', result.nt);
        data.append('nn', result.nn);
        data.append('nd', result.nd);
    } else {
        config.data = config.data || {};
        const result = encode(
            Date.now().toString(),
            config.url,
            config.method,
            JSON.stringify(config.headers),
            JSON.stringify(config.data),
        );

        data = {
            pm: config.method,
            ph: config.headers,
            pd: config.data,
            nv: result.nv,
            nt: result.nt,
            nn: result.nn,
            nd: result.nd,
        };
    }

    return {
        url: config.url,
        method: 'POST',
        headers: {},
        data,
    };
};
const main = async ()=>{
    const result =await nse({
        url: "https://api-cn-f2e-test.hungrypanda.cn/api/user/location",
        method: "GET",
        headers: {
            apptypeid: "1",
            authorization: "13ad986003c5ba9f5f28c5193c44ecdc",
            brand: "",
            countryCode: "US",
            language: "en",
            latitude: "30.203788170291023",
            longitude: "120.2169341262152",
            platform: "PC_WEB_USER",
            uniqueToken: "bd240567-80fc-48b2-b6e3-92a295211fb5",
            version: "8.8.0"
        },
        data: {}
    })

    console.log(result);
    // 返回结果，那这个去请求
    // {
    //     url: 'https://api-cn-f2e-test.hungrypanda.cn/api/user/location',
    //     method: 'POST',
    //     headers: {},
    //     data: {
    //         pm: 'GET',
    //             ph: {
    //             apptypeid: '1',
    //                 authorization: '13ad986003c5ba9f5f28c5193c44ecdc',
    //                 brand: '',
    //                 countryCode: 'US',
    //                 language: 'en',
    //                 latitude: '30.203788170291023',
    //                 longitude: '120.2169341262152',
    //                 platform: 'PC_WEB_USER',
    //                 uniqueToken: 'bd240567-80fc-48b2-b6e3-92a295211fb5',
    //                 version: '8.8.0'
    //         },
    //         pd: {},
    //         nv: '2',
    //             nt: '1749700023273',
    //             nn: 'dHF4hbqsu4sKXYFCQPjJ1W5JG',
    //             nd: '799afde331d9a29'
    //     }
    // }
}

main();