# jaxrsclientsample
## Overview
JAX-RS検証用のクライアント。[JAX-RS検証用サーバー](https://github.com/xsgk/jaxrsresourcesample)とセットで使用することを想定。Base64エンコードされた画像ファイルのアップロードリクエストをJSON形式で送信、同じくBase64エンコードされた画像ファイルをレスポンスとしてJSON形式で受信。

## How to use
クローンされたリポジトリへ移動し、

```
# cd jaxrsclientsample
```

ビルド用シェルを実行。[CentOS7](https://hub.docker.com/_/centos/) コンテナをベースとした検証用コンテナを作成し、コンテナの起動までが自動で実行される。

```
# ./build.sh
```

コンテナ起動後、ホストOSから直接実行

```
# docker exec jaxrsclientsample /opt/jaxrsclientsample/sh/jaxrsclientsample.sh
```

またはコンテナに入って実行する。正常に終了した場合、コンテナ中の `/opt/jaxrsclientsample/download` 配下にサーバーから受信したダウンロードデータ（*.png）が保存される。

```
# docker attach jaxrsclientsample
# /opt/jaxrsclientsample/sh/jaxrsclientsample.sh
```
