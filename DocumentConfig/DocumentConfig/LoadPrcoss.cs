using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Drawing.Drawing2D;
namespace WordTools
{
    [ToolboxItem(true)]
    public partial class LoadPrcoss : Control
    {
        private int count = -1;
        private ArrayList images = new ArrayList();
        public Bitmap[] bitmap = new Bitmap[8];
        private int _value = 1;
        private Color _circleColor = Color.Red;
        private float _circleSize = 0.6f;
        private PictureBox pictureBox;
        private Timer timer;
        public LoadPrcoss()
        {
            this.pictureBox = new PictureBox();
            this.BackColor = Color.FromArgb(255,255,255,255);
            this.timer = new System.Windows.Forms.Timer();
            this.timer.Interval = 500;
            this.pictureBox.Location = new System.Drawing.Point(0, 0);
            this.pictureBox.Name = "pictureBox";
            this.pictureBox.Size = new System.Drawing.Size(300,300);
            this.pictureBox.TabIndex = 0;
            this.pictureBox.TabStop = false;
            this.timer.Tick += new System.EventHandler(this.Timer_Tick);
            this.ClientSize = new System.Drawing.Size(300, 300);
            this.Controls.Add(this.pictureBox);
            this.Visible = true;
        }
        public void Start(string message)
        {
            this.timer.Enabled = true;
            images.Clear();
            set(message);
        }
        public void End()
        {
            this.timer.Enabled = false;
        }
        public Color CircleColor
        {
            get { return _circleColor; }
            set
            {
                _circleColor = value;
                Invalidate();
            }
        }
 
        public float CircleSize
        {
            get { return _circleSize; }
            set
            {
                if (value <= 0.0F)
                    _circleSize = 0.05F;
                else
                    _circleSize = value > 4.0F ? 4.0F : value;
                Invalidate();
            }
        }
         public void Draw(string message)
        {
            for (int j = 0; j < 8; j++)
            {
                bitmap[7-j] = DrawCircle(j,message);
            }
        }
        protected override void OnResize(EventArgs e)
        {
            SetNewSize();
            base.OnResize(e);
        }
 
        protected override void OnSizeChanged(EventArgs e)
        {
            SetNewSize();
            base.OnSizeChanged(e);
        }
 
        private void SetNewSize()
        {
            int size = Math.Max(Width, Height);
            Size = new Size(size, size);
        }
 
        public void set(string message)
        {
            for (int i = 0; i < 8; i++)
            {
                Draw(message);
 
                Bitmap map = new Bitmap((bitmap[i]), new Size(300, 300));
 
                images.Add(map);
            }
           pictureBox.Image = (Image)images[0];
           pictureBox.Size = pictureBox.Image.Size;
 
        }
        private void Timer_Tick(object sender, EventArgs e)
        {
           
            count = (count + 1) % 8;
            pictureBox.Image = (Image)images[count];
 
        }
        public Bitmap DrawCircle(int j,string message)
        {
            const float angle = 360.0F / 8;
            Bitmap map = new Bitmap(300, 300);
            Graphics g = Graphics.FromImage(map);
            Color textcolor = Color.FromArgb(180, Color.Blue);
            SolidBrush textbrush = new SolidBrush(textcolor);
            g.DrawString(message, new Font("Arial", 10), textbrush, new Point(120, 155));
            g.TranslateTransform(Width / 2.0F, Height / 2.0F);
            g.RotateTransform(angle * _value);
            g.InterpolationMode = InterpolationMode.HighQualityBicubic;
            g.SmoothingMode = SmoothingMode.AntiAlias;
            int[] a = new int[8] { 25, 50, 75, 100, 125, 150, 175, 200 };
            for (int i = 1; i <= 8; i++)
            {
                int alpha = a[(i + j - 1) % 8];
                Color drawColor = Color.FromArgb(alpha, _circleColor);
               
                using (SolidBrush brush = new SolidBrush(drawColor))
                {
                   
                    float sizeRate = 3.5F / _circleSize;
                    float size = Width / (6 * sizeRate);
 
                    float diff = (Width / 10.0F) - size;
 
                    float x = (Width / 80.0F) + diff;
                    float y = (Height / 80.0F) + diff;
                    g.FillEllipse(brush, x, y, size, size);
                    g.RotateTransform(angle);
                }
            }
           
            return map;
        }
    }
}
