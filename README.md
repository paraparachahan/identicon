# Identicon

ユーザー名をもとに、GitHub の Identicon のような左右対称のアイコンを生成する Java プログラムです。

Java の学習を兼ねて、ハッシュ値・ビット演算・2次元配列・画像生成などを使用して実装しています。

> 🚧 **現在リファクタリング中です。**
>
> 動作する初期版をもとに、コード構成や設計の改善を進めています。

## Features

- ユーザー名から一意なIdenticonを生成
- 7×7の左右対称パターン生成
- ハッシュ値を利用した色決定
- PNG形式で画像を出力
- 配置方法の異なる2種類のアイコンを生成

## Sample

入力:

```text
Last Name  : YAMADA
First Name : TARO
```

内部では以下の形式に変換して使用します。

```text
Use this name : YAMADA:TARO
```

生成されたIdenticon:

![YAMADA:TARO Identicon A](docs/images/iconA.png)
![YAMADA:TARO Identicon B](docs/images/iconB.png)

## How it works

処理の大まかな流れは以下の通りです。

```text
名字 + 名前
    ↓
文字列を結合
    ↓
hashCode()
    ↓
32bit のハッシュ値
    ↓
┌──────────────────┬──────────┐
│ 28bit            │ 4bit     │
│ pattern generate │ color    │
└──────────────────┴──────────┘
    ↓
7×7 の左右対称パターンを生成
    ↓
PNGとして出力
```

## Pattern generation

Java の `String#hashCode()` から得られる32bitの値を利用しています。

- 上位28bit：アイコンの配置
- 下位4bit：色の選択

7×7のアイコンを左右対称にするため、ハッシュ値から決定するのは片側3列と中央1列の合計28マスです。

```text
A B C D C B A
```

現在は、28bitの配置方法を変更した2種類のパターンを生成しています。

## Color

ハッシュ値の下位4bitを利用し、0〜15の値を取得します。

その値を16色の固定カラーパレットに対応させ、Identiconの色を決定しています。

```text
4bit
 ↓
0 ～ 15
 ↓
16色のカラーパレットから1色選択
```

## Output

生成された画像はPNG形式で保存されます。

```text
iconA.png
iconB.png
```

画像サイズ:

- アイコン本体：7×7セル
- 外側余白：上下左右1セル
- 全体：9×9セル
- 1セル：40px
- 画像サイズ：360×360px

## Technologies

- Java
- Maven
- `java.awt.Graphics2D`
- `java.awt.image.BufferedImage`
- `javax.imageio.ImageIO`

## How to run

実行手順:

1. Repositoryをclone
2. Java / Maven環境を準備
3. プログラムを実行
4. 名字と名前を入力
5. PNGファイルを生成

### Headless環境での実行

このプログラムでは、PNG画像を生成するために `BufferedImage` や `Graphics2D` などの `java.awt` 関連クラスを使用しています。

GUI環境のないLinuxサーバーやコンテナ環境などでは、AWTの初期化時にエラーが発生する場合があります。

その場合は、Headlessモードで実行してください。

```bash
java -Djava.awt.headless=true -jar <jarファイル名>.jar
```

このプログラムではウィンドウ表示は行わず、AWTはメモリ上でIdenticonを描画し、PNGとして保存するために使用しています。

## Project structure

現在はシンプルな構成ですが、リファクタリングに伴い変更する可能性があります。

```text
identicon/
├─ src/
│  └─ main/
│     └─ java/
│        └─ com/example/identicon/
│           └─ Main.java
├─ pom.xml
├─ README.md
└─ .gitignore
```

## Refactoring

現在、初期実装をもとにコード構成の改善を進めています。

主な改善項目:

- `main` メソッドに集中している処理の分割
- Identicon生成処理のメソッド化
- PNG出力処理の共通化
- 変数名の整理
- マジックナンバーの整理
- デバッグ用出力の削除
- 入力処理の整理
- 必要に応じたクラス分割

## Purpose

このプロジェクトは、本格的なIdenticonライブラリを作成することを目的としたものではありません。

Javaの学習・個人開発の練習として、以下の内容を実際に使用して理解することを目的にしています。

- ハッシュ値
- 2進数
- ビット演算
- `&`
- `>>>`
- 2次元配列
- 左右対称なデータ生成
- `BufferedImage`
- `Graphics2D`
- PNGファイル出力
- Git / GitHub
- Maven

## Version

Current version: v1.0.0

## License

未設定です。
